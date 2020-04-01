import java.text.DecimalFormat;
import java.util.*;
import java.io.*;

public class Supplier implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String SUPPLIER_STRING = "S";
	private String id,
            name,
            phone,
            address;
	
	/*
     * Function:	Supplier
     * Type:		constructor(generic)
     * Privacy:		public
     * Description:	Supplier Constructor.
	 */	
    Supplier(String name, String phone, String address){
        id = SUPPLIER_STRING + (SupplierIdServer.instance()).getId();
        this.name = name;
        this.phone = phone;
        this.address = address;
    }//end constuctor
	
	/*
     * Function:	getId
     * Type:		String
     * Privacy:		public
     * Description:	Gets Supplier ID.
	 */
	public String getId(){
		return id;
	}//end getId
	
	/*
     * Function:	getName
     * Type:		String
     * Privacy:		public
     * Description:	Gets Supplier Name.
	 */
	public String getName(){
		return name;
	}//end getName
	
	/*
     * Function:	getPhone
     * Type:		String
     * Privacy:		public
     * Description:	Gets Supplier Phone Number.
	 */
	public String getPhone(){
		return phone;
	}//end getPhone
	
	/*
     * Function:	getAddress
     * Type:		String
     * Privacy:		public
     * Description:	Gets Supplier Address.
	 */
	public String getAddress(){
		return address;
	}//end getAddress
	
	/*
     * Function:	setId
     * Type:		void
     * Privacy:		public
     * Description:	Sets Supplier ID.
	 */
	public void setId(String id){
		this.id = id;
	}//end setId
	
	/*
     * Function:	setName
     * Type:		void
     * Privacy:		public
     * Description:	Sets Supplier Name.
	 */
	public void setName(String name){
		this.name = name;
	}//end setName
	
	/*
     * Function:	setPhone
     * Type:		void
     * Privacy:		public
     * Description:	Sets Supplier Phone Number.
	 */
	public void setPhone(String phone){
		this.phone = phone;
	}//end setPhone
	
	/*
     * Function:	setAddress
     * Type:		void
     * Privacy:		public
     * Description:	Sets Supplier Address.
	 */
	public void setAddress(String address){
		this.address = address;
	}//end setAddress

    /*
     * Function:	toString
     * Type:		String
     * Privacy:		public
     * Description:	Converts Supplier to string output.
	 */
    public String toString(){
        return "ID: " + id + " Name: " + name + " Phone Number: " + phone + " Address: " + address;
    }//end toString
}