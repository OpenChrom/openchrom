package net.sf.kerner.utils.collections.filter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.kerner.utils.collections.list.UtilList;

public class FilterCollection {

	/**
	 * Filters given {@link Collection} using given {@link Filter}. Removes all
	 * <b>not</b> matching elements via {@link Iterator#remove()}. <br>
	 * Note: Synchronizes on {@code collection}.
	 *
	 * @param collection
	 * @param filter
	 */
	public static <C> void filterCollectionRemove(final Collection<? extends C> collection, final Filter<C> filter) {

		if(collection == null) {
			throw new NullPointerException();
		}
		synchronized(collection) {
			for(final Iterator<? extends C> i = collection.iterator(); i.hasNext();) {
				if(filter.filter(i.next())) {
					// OK
				} else {
					i.remove();
				}
			}
		}
	}

	/**
	 * Filters given {@link Collection} using given {@link Filter}. All
	 * <b>matching</b> elements are returned in a new {@code collection}. <br>
	 * Note: Synchronizes on {@code collection}.
	 *
	 * @param collection
	 *            {@link Collection} that is filtered
	 * @param filter
	 *            {@link Filter} which is used for filtering
	 * @return A new {@link Collection}, that contains all matching elements
	 */
	public static <C> List<C> filterCollectionReturn(final Collection<? extends C> collection, final Filter<C> filter) {

		final List<C> result = UtilList.newList();
		if(collection == null) {
			throw new NullPointerException();
		}
		synchronized(collection) {
			for(final Iterator<? extends C> i = collection.iterator(); i.hasNext();) {
				final C next = i.next();
				if(filter.filter(next)) {
					result.add(next);
				}
			}
		}
		return result;
	}

	private FilterCollection() {

	}
}
