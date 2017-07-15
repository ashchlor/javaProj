package com.hit.memoryunits;

public class Page<T> implements java.io.Serializable {
	
	
	private Long _id;
	private T _content;
	
	public Page(long Id, T content) {
		_id = Id;
		_content = content;
	}

	public T getContent()
	{
		return _content;
	}
	
	public long getId()
	{
		return _id;
	}
	
	@Override
	public String toString() {
		return "pageId=" + _id + "content=" + _content;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int prime = 31;
		int result = 1;
		result = (int) (prime * result + this._id.hashCode());
		return result;
	}
	
	public void setContent(T content)
	{
		_content = content;
	}
	
	public void setId(long Id)
	{
		_id	= Id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Page )
		{
			if( ((Page)obj).getContent().equals(_content) &&
					((Page)obj).getId() == _id)
			{
				return true;
			}
			else
				return false;
		}
		else
		{
			return false;
		}
	}
}
