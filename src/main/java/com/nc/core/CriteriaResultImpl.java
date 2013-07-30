package com.nc.core;

import com.nc.core.expressions.NativeProjection;

import java.util.Date;
import java.util.List;

/**
 * Implementation CriteriaResult.
 */
public class CriteriaResultImpl implements CriteriaResult
{
	
	/** Query results. */
	private List<Object[]> results;
	
	/** Record index. */
	private int recordIdx = -1;
	
	/** Projections. */
	private NativeProjection projection;
	
	/**
	 * Constructor.
	 *
	 * @param results the results
	 * @param projection the projection
	 */
	public CriteriaResultImpl(List<Object[]> results, NativeProjection projection)
	{
		if (results == null)
			throw new IllegalStateException("Results is null!");
		
		this.results = results;
		this.projection = projection;
	}
	
	/**
	 * Check is next record available.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean next()
	{
		recordIdx++;
		return recordIdx < results.size();
	}
	
	@Override
	public Boolean getBoolean(int idx, Boolean defaultResult)
	{
		Object val = getValue(idx, null);
		if (val == null)
			return defaultResult;
		
		Boolean res;
		if (val instanceof String)
			res = Boolean.valueOf(val.toString());
		else
		{
			String v = val.toString();
			res = v.equals("1");
		}
		
		return res;
	}
	
	@Override
	public Date getDate(int idx, Date defaultResult)
	{
		Object val = getValue(idx, null);
		if (val == null)
			return defaultResult;
			
		return (Date) val;
	}

	@Override
	public Integer getInteger(int idx, Integer defaultResult)
	{
		Object val = getValue(idx, null);
		if (val == null)
			return defaultResult;
		
		return Integer.parseInt(val.toString());
	}
	
	@Override
	public Double getDouble(int idx, Double defaultResult)
	{
		Object val = getValue(idx, null);
		if (val == null)
			return defaultResult;
		
		return Double.valueOf(val.toString());
	}

	@Override
	public Double getDouble(String columnName, Double defaultResult)
	{
		Object val = getValue(columnName, null);
		if (val == null)
			return defaultResult;
		
		return Double.valueOf(val.toString());
	}

	@Override
	public Long getLong(int idx, Long defaultResult)
	{
		Object val = getValue(idx, null);
		if (val == null)
			return defaultResult;
		
		return Long.parseLong(val.toString());
	}

	@Override
	public Short getShort(int idx, Short defaultResult)
	{
		Object val = getValue(idx, null);
		if (val == null)
			return defaultResult;
		
		return Short.parseShort(val.toString());
	}

	@Override
	public String getString(int idx, String defaultResult)
	{
		Object val = getValue(idx, null);
		if (val == null)
			return defaultResult;
		
		return val.toString();
	}
	
	@Override
	public Object getValue(int idx, Object defaultResult)
	{
		if (recordIdx == -1 || recordIdx >= results.size())
			throw new IndexOutOfBoundsException("Record out of range!. RecordIdx="+recordIdx+", recordSize="+results.size());
		
		if (projection == null || projection.countProjections() > 1)
		{
			Object[] row = results.get(recordIdx);
			if (idx < 0 || idx >= row.length)
				throw new IndexOutOfBoundsException("Value out of range in record!");
			
			if (row[idx] == null)
				return defaultResult;
			
			return row[idx];
		}
		else
		{
			Object val = results.get(recordIdx);
			if (val == null)
				return defaultResult;
			
			return val;
		}
	}
	
	@Override
	public Integer getRowsNumber()
	{
		return results.size();
	}

	@Override
	public Boolean getBoolean(String columnName, Boolean defaultResult)
	{
		if (projection == null)
			throw new IllegalStateException(
					"Only supported method with a fixed projection!");
		
		return getBoolean(projection.getProjectionIndex(columnName), defaultResult);
	}

	@Override
	public Date getDate(String columnName, Date defaultResult)
	{
		if (projection == null)
			throw new IllegalStateException(
					"Only supported method with a fixed projection!");
		return getDate(projection.getProjectionIndex(columnName), defaultResult);
	}

	@Override
	public Integer getInteger(String columnName, Integer defaultResult)
	{
		if (projection == null)
			throw new IllegalStateException(
					"Only supported method with a fixed projection!");
		return getInteger(projection.getProjectionIndex(columnName), defaultResult);
	}

	@Override
	public Long getLong(String columnName, Long defaultResult)
	{
		if (projection == null)
			throw new IllegalStateException(
					"Only supported method with a fixed projection!");
		return getLong(projection.getProjectionIndex(columnName), defaultResult);
	}

	@Override
	public Short getShort(String columnName, Short defaultResult)
	{
		if (projection == null)
			throw new IllegalStateException(
					"Only supported method with a fixed projection!");
		return getShort(projection.getProjectionIndex(columnName), defaultResult);
	}

	@Override
	public String getString(String columnName, String defaultResult)
	{
		if (projection == null)
			throw new IllegalStateException(
					"Only supported method with a fixed projection!");
		return getString(projection.getProjectionIndex(columnName), defaultResult);
	}

	@Override
	public Object getValue(String columnName, Object defaultResult)
	{
		if (projection == null)
			throw new IllegalStateException(
					"Only supported method with a fixed projection!");
		return getValue(projection.getProjectionIndex(columnName), defaultResult);
	}

}