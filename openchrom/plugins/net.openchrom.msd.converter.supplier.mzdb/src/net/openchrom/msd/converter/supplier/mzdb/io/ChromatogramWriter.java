/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mzdb.io;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.converter.io.AbstractChromatogramWriter;
import org.eclipse.chemclipse.converter.l10n.ConverterMessages;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.msd.converter.io.IChromatogramMSDWriter;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IRegularMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.support.history.IEditInformation;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.mzdb.Activator;

public class ChromatogramWriter extends AbstractChromatogramWriter implements IChromatogramMSDWriter {

	private static final Logger logger = Logger.getLogger(ChromatogramWriter.class);

	@Override
	public void writeChromatogram(File file, IChromatogramMSD chromatogram, IProgressMonitor monitor) throws FileIsNotWriteableException, IOException {

		try {
			Class.forName("org.sqlite.JDBC");
		} catch(ClassNotFoundException e) {
			logger.error(e);
			return;
		}
		if(file.exists()) {
			if(!file.delete()) { // otherwise it would try to append
				logger.error("Failed to delete file.");
			}
		}
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + file.toString())) {
			connection.setAutoCommit(false);
			createMzDB(connection, chromatogram);
			createSample(connection, chromatogram);
			createSoftware(connection);
			createDataProcessing(connection, chromatogram);
			createSpectrum(connection, chromatogram, monitor);
			connection.commit();
			connection.setAutoCommit(true);
		} catch(SQLException e) {
			logger.error(e);
		}
	}

	private void createDataProcessing(Connection connection, IChromatogramMSD chromatogram) throws SQLException {

		String createDataProcessing = """
				CREATE TABLE data_processing (
				  id INTEGER PRIMARY KEY AUTOINCREMENT,
				  name TEXT NOT NULL
				)
				""";
		try (var statement = connection.createStatement()) {
			statement.execute(createDataProcessing);
		}
		String insert = "INSERT INTO data_processing(id,name) VALUES(?,?)";
		try (var prepareStatement = connection.prepareStatement(insert)) {
			for(IEditInformation editInformation : chromatogram.getEditHistory()) {
				prepareStatement.setString(2, editInformation.getDescription());
				prepareStatement.addBatch();
			}
			prepareStatement.setString(2, "Convert to mzDB");
			prepareStatement.addBatch();
			prepareStatement.executeBatch();
		}
	}

	private void createMzDB(Connection connection, IChromatogramMSD chromatogram) throws SQLException {

		String createMzDB = """
				CREATE TABLE mzdb (
				  version TEXT(10) NOT NULL,
				  creation_timestamp TEXT NOT NULL,
				  file_content TEXT NOT NULL,
				  contacts TEXT NOT NULL,
				  param_tree TEXT NOT NULL,
				  PRIMARY KEY (version)
				)
				""";
		try (var statement = connection.createStatement()) {
			statement.execute(createMzDB);
		}
		String insert = "INSERT INTO mzdb(version,creation_timestamp,file_content,contacts,param_tree) VALUES(?,?,?,?,?)";
		try (var prepareStatement = connection.prepareStatement(insert)) {
			prepareStatement.setString(1, "0.7");
			prepareStatement.setLong(2, chromatogram.getDate().getTime());
			prepareStatement.setString(3, ""); // TODO
			prepareStatement.setString(4, chromatogram.getOperator());
			prepareStatement.setString(5, ""); // TODO
			prepareStatement.executeUpdate();
		}
	}

	private void createSample(Connection connection, IChromatogramMSD chromatogram) throws SQLException {

		String createSample = """
				CREATE TABLE sample (
				  id INTEGER PRIMARY KEY AUTOINCREMENT,
				  name TEXT NOT NULL,
				  param_tree TEXT,
				  shared_param_tree_id INTEGER,
				  FOREIGN KEY (shared_param_tree_id) REFERENCES shared_param_tree (id)
				)
				 """;
		try (var statement = connection.createStatement()) {
			statement.execute(createSample);
		}
		String insert = "INSERT INTO sample(id,name,param_tree,shared_param_tree_id) VALUES(?,?,?,?)";
		try (var prepareStatement = connection.prepareStatement(insert)) {
			prepareStatement.setString(2, chromatogram.getSampleName());
			prepareStatement.executeUpdate();
		}
	}

	private void createSoftware(Connection connection) throws SQLException {

		String createSoftware = """
				CREATE TABLE software (
				  id INTEGER PRIMARY KEY AUTOINCREMENT,
				  name TEXT NOT NULL,
				  version TEXT NOT NULL,
				  param_tree TEXT NOT NULL,
				  shared_param_tree_id INTEGER,
				  FOREIGN KEY (shared_param_tree_id) REFERENCES shared_param_tree (id)
				)
				""";
		try (var statement = connection.createStatement()) {
			statement.execute(createSoftware);
		}
		String insert = "INSERT INTO software(id,name,version,param_tree,shared_param_tree_id) VALUES(?,?,?,?,?)";
		try (var prepareStatement = connection.prepareStatement(insert)) {
			prepareStatement.setString(2, "OpenChrom");
			prepareStatement.setString(3, Activator.getContext().getBundle().getVersion().toString());
			prepareStatement.setString(4, "");// TODO
			prepareStatement.setInt(5, 0); // TODO
			prepareStatement.executeUpdate();
		}
	}

	private void createSpectrum(Connection connection, IChromatogramMSD chromatogram, IProgressMonitor monitor) throws SQLException {

		String createSpectrum = """
				CREATE TABLE spectrum(
				  id INT,
				  initial_id INT,
				  title TEXT,
				  cycle INT,
				  time REAL,
				  ms_level INT,
				  activation_type TEXT,
				  tic REAL,
				  base_peak_mz REAL,
				  base_peak_intensity REAL,
				  main_precursor_mz REAL,
				  main_precursor_charge INT,
				  data_points_count INT,
				  param_tree TEXT,
				  scan_list TEXT,
				  precursor_list TEXT,
				  product_list TEXT,
				  shared_param_tree_id INT,
				  instrument_configuration_id INT,
				  source_file_id INT,
				  run_id INT,
				  data_processing_id INT,
				  data_encoding_id INT,
				  bb_first_spectrum_id INT
				)
				""";
		try (var statement = connection.createStatement()) {
			statement.execute(createSpectrum);
		}
		monitor.beginTask(ConverterMessages.writeScans, chromatogram.getNumberOfScans());
		String insert = "INSERT INTO spectrum(cycle,title,time,ms_level,tic,main_precursor_mz) VALUES(?,?,?,?,?,?)";
		try (var prepareStatement = connection.prepareStatement(insert)) {
			for(IScan scan : chromatogram.getScans()) {
				prepareStatement.setFloat(5, scan.getTotalSignal());
				if(scan instanceof IScanMSD scanMSD) {
					prepareStatement.setInt(1, scanMSD.getScanNumber());
					prepareStatement.setString(2, scanMSD.getIdentifier());
					prepareStatement.setFloat(3, scanMSD.getRetentionTime() / 1000f); // ms to s
					if(scanMSD instanceof IRegularMassSpectrum regularMassSpectrum) {
						prepareStatement.setShort(4, regularMassSpectrum.getMassSpectrometer());
						prepareStatement.setDouble(6, regularMassSpectrum.getPrecursorIon());
					}
				}
				prepareStatement.addBatch();
				monitor.worked(1);
			}
			prepareStatement.executeBatch();
		}
	}
}
