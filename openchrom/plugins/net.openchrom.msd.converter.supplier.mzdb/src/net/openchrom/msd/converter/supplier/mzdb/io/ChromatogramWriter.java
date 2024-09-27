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
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IRegularMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.support.history.IEditInformation;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.mzdb.Activator;
import net.openchrom.msd.converter.supplier.mzdb.internal.DataMode;

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
			createSpectrum(connection, chromatogram);
			createDataEncoding(connection);
			createBoundingBox(connection, chromatogram, monitor);
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

	private void createSpectrum(Connection connection, IChromatogramMSD chromatogram) throws SQLException {

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
			}
			prepareStatement.executeBatch();
		}
	}

	private void createDataEncoding(Connection connection) throws SQLException {

		String createDataEncoding = """
				CREATE TABLE data_encoding (
				  id INTEGER PRIMARY KEY AUTOINCREMENT,
				  mode TEXT(10) NOT NULL,
				  compression TEXT,
				  byte_order TEXT(13) NOT NULL,
				  mz_precision INTEGER NOT NULL,
				  intensity_precision INTEGER NOT NULL,
				  param_tree TEXT
				)
				""";
		try (var statement = connection.createStatement()) {
			statement.execute(createDataEncoding);
		}
		String insert = "INSERT INTO data_encoding(mode,compression,byte_order,mz_precision,intensity_precision) VALUES(?,?,?,?,?)";
		try (var prepareStatement = connection.prepareStatement(insert)) {
			prepareStatement.setString(1, DataMode.CENTROID.name().toLowerCase());
			prepareStatement.setString(2, "none");
			prepareStatement.setString(3, "little_endian");
			prepareStatement.setInt(4, 64);
			prepareStatement.setInt(5, 32);
			prepareStatement.executeUpdate();
		}
	}

	private void createBoundingBox(Connection connection, IChromatogramMSD chromatogram, IProgressMonitor monitor) throws SQLException {

		String createBoundingBox = """
				CREATE TABLE bounding_box (
				  id INTEGER PRIMARY KEY AUTOINCREMENT,
				  data BLOB NOT NULL,
				  run_slice_id INTEGER NOT NULL,
				  first_spectrum_id INTEGER NOT NULL,
				  last_spectrum_id INTEGER NOT NULL,
				  FOREIGN KEY (run_slice_id) REFERENCES run_slice (id),
				  FOREIGN KEY (first_spectrum_id) REFERENCES spectrum (id),
				  FOREIGN KEY (last_spectrum_id) REFERENCES spectrum (id)
				)
				""";
		try (var statement = connection.createStatement()) {
			statement.execute(createBoundingBox);
		}
		monitor.beginTask(ConverterMessages.writeScans, chromatogram.getNumberOfScans());
		String insert = "INSERT INTO bounding_box(run_slice_id, data, first_spectrum_id, last_spectrum_id) VALUES(?,?,?,?)";
		try (var prepareStatement = connection.prepareStatement(insert)) {
			// TODO: optimize slicing for data retrieval
			for(IScan scan : chromatogram.getScans()) {
				prepareStatement.setFloat(1, scan.getScanNumber());
				if(scan instanceof IScanMSD scanMSD) {
					prepareStatement.setBytes(2, scanToBlob(scanMSD));
				}
				prepareStatement.setFloat(3, scan.getScanNumber());
				prepareStatement.setFloat(4, scan.getScanNumber());
				monitor.worked(1);
				prepareStatement.addBatch();
			}
			prepareStatement.executeBatch();
		}
	}

	private static byte[] scanToBlob(IScanMSD scanMSD) {

		int headerSize = Integer.SIZE * 2;
		int mzSize = Double.SIZE / Byte.SIZE * scanMSD.getNumberOfIons();
		int intensitySize = Float.SIZE / Byte.SIZE * scanMSD.getNumberOfIons();
		ByteBuffer buffer = ByteBuffer.allocate(headerSize + mzSize + intensitySize);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(scanMSD.getScanNumber());
		buffer.putInt(scanMSD.getNumberOfIons());
		for(IIon ion : scanMSD.getIons()) {
			buffer.putDouble(ion.getIon());
			buffer.putFloat(ion.getAbundance());
		}
		return buffer.array();
	}
}
