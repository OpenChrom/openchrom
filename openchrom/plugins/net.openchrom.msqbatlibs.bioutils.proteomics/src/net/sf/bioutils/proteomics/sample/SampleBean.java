package net.sf.bioutils.proteomics.sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.sf.bioutils.proteomics.User;
import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.kerner.utils.UtilString;
import net.sf.kerner.utils.collections.UtilCollection;
import net.sf.kerner.utils.collections.map.MapList;

public class SampleBean implements SampleModifiable {

	private static long cnt = 0;
	private final long id;

	@Override
	public RawSample getRawSample() {

		synchronized(this) {
			return rawSample;
		}
	}

	public void setRawSample(RawSample rawSample) {

		synchronized(this) {
			this.rawSample = rawSample;
		}
	}

	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	private MapList<String, Object> properties = new MapList<String, Object>();
	private String name;
	private String nameBase;
	private User user;
	private RawSample rawSample;
	private List<Peak> peaks = new ArrayList<Peak>();

	public SampleBean() {

		this(null, null, null, null, null);
	}

	public SampleBean(final Sample template) {

		this(template.getName(), template.getUser(), template.getNameBase(), template.getPeaks(), template.getProperties());
	}

	public SampleBean(final Sample template, final String newName) {

		this(newName, template.getUser(), template.getNameBase(), template.getPeaks(), template.getProperties());
	}

	public SampleBean(final Sample template, final String newName, final boolean empty) {

		this(newName, template.getUser(), template.getNameBase(), null, template.getProperties());
		if(!empty) {
			setPeaks(template.getPeaks());
		}
	}

	public SampleBean(final String name) {

		this(name, null, null, null, null);
	}

	public SampleBean(final String name, final Collection<? extends Peak> peaks) {

		this(name, null, null, peaks, null);
	}

	public SampleBean(final String name, final User user, final String baseName) {

		this(name, user, baseName, null, null);
	}

	public SampleBean(final String name, final User user, final String baseName, final Collection<? extends Peak> peaks, final MapList<String, Object> properties) {

		this.id = cnt++;
		if(properties != null) {
			this.properties.putAll(properties);
		}
		this.name = name;
		if(UtilString.emptyString(baseName)) {
			nameBase = name;
		} else {
			nameBase = baseName;
		}
		this.user = user;
		if(UtilCollection.notNullNotEmpty(peaks)) {
			this.peaks = new ArrayList<Peak>(peaks.size());
			addPeaks(peaks);
		}
	}

	@Override
	public long getId() {

		return id;
	}

	@Override
	public void addPeak(final Peak peak) {

		synchronized(this) {
			peaks.add(peak);
			peak.setSample(this);
		}
	}

	@Override
	public void addPeaks(final Collection<? extends Peak> peaks) {

		this.peaks.addAll(peaks);
		for(final Peak p : peaks) {
			p.setSample(this);
		}
	}

	@Override
	public SampleBean clone() {

		return clone(getName());
	}

	@Override
	public SampleBean clone(final String newName) {

		final SampleBean result = new SampleBean(newName, getUser(), getNameBase(), null, getProperties());
		for(final Peak p : getPeaks()) {
			// if (p instanceof PeakAnnotatable && !((PeakAnnotatable)
			// p).getAnnotation().isEmpty()) {
			// final int i = 0;
			// }
			result.addPeak(p.clone());
		}
		return result;
	}

	/**
	 * Clones this {@code SampleBean} without peaks.
	 *
	 * @return a clone of this {@code SampleBean}, that contains no peaks
	 */
	@Override
	public SampleBean cloneWOPeaks(final String newName) {

		final SampleBean result = new SampleBean(newName, getUser(), getNameBase(), null, getProperties());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {

		return new EqualatorSample().areEqual(this, obj);
	}

	@Override
	public ReadWriteLock getLock() {

		return lock;
	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public String getNameBase() {

		if(nameBase == null) {
			return getName();
		}
		return nameBase;
	}

	@Override
	public List<Peak> getPeaks() {

		return peaks;
	}

	@Override
	public MapList<String, Object> getProperties() {

		return properties;
	}

	@Override
	public int getSize() {

		return peaks.size();
	}

	@Override
	public User getUser() {

		return user;
	}

	@Override
	public int hashCode() {

		return new HashCalculatorSample().calculateHash(this);
	}

	@Override
	public void removePeak(final Peak peak) {

		peaks.remove(peak);
	}

	@Override
	public void removePeaks(final Collection<? extends Peak> peaks) {

		this.peaks.removeAll(peaks);
	}

	@Override
	public void setName(final String name) {

		this.name = name;
	}

	@Override
	public void setNameBase(final String nameBase) {

		this.nameBase = nameBase;
	}

	@Override
	public void setPeaks(final List<Peak> peaks) {

		synchronized(this) {
			for(final Peak p : peaks) {
				addPeak(p);
			}
		}
	}

	@Override
	public void setProperties(final MapList<String, Object> properties) {

		this.properties = properties;
	}

	@Override
	public void setUser(final User user) {

		this.user = user;
	}

	@Override
	public String toString() {

		return getName() + ", " + getSize();
	}
}
