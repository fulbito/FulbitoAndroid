package herramientas;

public class ItemMenuLateral {
	private String sTitulo;
	private int iIcono;
	
	public ItemMenuLateral(String sTitulo, int iIcono)
	{
		this.sTitulo = sTitulo;
		this.iIcono = iIcono;
	}
	
	public String getTitulo()
	{
		return sTitulo;
	}
	
	public int getIcono()
	{
		return iIcono;
	}
	
	public void setTitulo(String sTitulo)
	{
		this.sTitulo = sTitulo;
	}
	
	public void setIcono(int iICono)
	{
		this.iIcono = iICono;
	}
}
