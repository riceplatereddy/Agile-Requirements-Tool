package art.dashboard;
/******************************************************************************************************************
 * Copy rights @ Team ART
 *       
 * Description:
 *		This Class embeds all operations on objects and their attributes
 *           
 * Internal Methods :
 *		
 *
 * Reviewed by : 
 * 
 * Review Comments :
 *
 ******************************************************************************************************************/
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import art.datastructures.BOAttribute;
import art.datastructures.BusinessObject;

@ManagedBean
@SessionScoped
public class ObjectOperations implements Serializable{
	private static final List<BusinessObject> businessObjects = new ArrayList<BusinessObject>();
	/*this variable is used to bind front end input where user creates a new attribute by
	 *entering name of business object
	 */
	private Map<String,String> dropDownObjectTypes;
	private String objectName;
	private String objectType;
	/*this variable is used to identify which BO is selected when
	 * user clicks on edit attributes in home page
	 * */
	private BusinessObject selectedBO;
	
	//For attribute operations
	private BOAttribute attribute = new BOAttribute();
//	private List<BOAttribute> attributes = new ArrayList<BOAttribute>();
	
	public ObjectOperations() {
		//initialize drop down menu
		dropDownObjectTypes = new HashMap<String, String>();
		dropDownObjectTypes.put("Transaction Business Object", "Transaction Business Object");
		dropDownObjectTypes.put("Core Business Object (Default)", "Core Business Object");
		
		List<BOAttribute> temp = new ArrayList<BOAttribute>();
		temp.add(new BOAttribute(1, "Name", "String", "NA", "length<20"));
		temp.add(new BOAttribute(1, "ID", "Integer", "NA", "length<20"));

		businessObjects.add(new BusinessObject(1,"BO1","Core Business Object",temp));
		businessObjects.add(new BusinessObject(2,"BO2","Transaction Business Object",temp));
		businessObjects.add(new BusinessObject(3,"BO3","Core Business Object",temp));
		businessObjects.add(new BusinessObject(4,"BO4","Core Business Object",temp));
		businessObjects.add(new BusinessObject(5,"BO5","Transaction Business Object",temp));
	}

	/***************************************************************************
	* Method:  	addObject
	* 
	* Purpose: 	This method takes care of adding a business object to current list
	* 		   	check if a business object with same name already exist
	* 
	* Attributes: 
	* 		   	objectName - this is the attribute which contains business object
	* 			name as provided by user from front end
	* 
	* checks:	1.Checks if method is invoked with empty name
	* 			2.Checks if a business object with same name already exists
	* 
	* Notes:	1.Need to add db code
	****************************************************************************/
	public String addObject() {
		if (objectName.equals("")) {
			FacesMessage msg = new FacesMessage("Please enter Business Object Name");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else if(objectType.equals("")){
			FacesMessage msg = new FacesMessage("Please enter Business Object Type");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else {
			boolean exists = false;
			for(BusinessObject object : businessObjects){
				if(object.getObjectName().equalsIgnoreCase(objectName))
					exists = true;
			}
			if(exists){
				FacesMessage msg = new FacesMessage("Business Object with name "+objectName+" already exists.Please choose a different name");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
				businessObjects.add(new BusinessObject(businessObjects.size()+1,objectName,objectType,new ArrayList<BOAttribute>()));
				objectName = "";
			}
		}
		return null;
	}

	/***************************************************************************
	* Method:  	onEdit
	* 
	* Purpose: 	This method is invoked when user opts to edit a row in datatable
	* 			User will be able to just edit name of the object
	* 
	* Attributes: 
	* 
	* checks:	
	* 
	* Notes:	1.Need to add db code
	****************************************************************************/
	public void onEdit(RowEditEvent event) {
		BusinessObject objectSelected = (BusinessObject) event.getObject();
		if (objectSelected.getObjectName().equals("")) {
			FacesMessage msg = new FacesMessage("Please enter a name");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			boolean exists = false;
			for(BusinessObject object : businessObjects){
				if(object.getObjectName().equalsIgnoreCase(objectName))
					exists = true;
			}
			if(exists){
				FacesMessage msg = new FacesMessage("Business Object with name "+objectSelected.getObjectName()+" already exists.Please choose a different name");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
				for(BusinessObject object : businessObjects){
					if(object.getObjectID() == objectSelected.getObjectID())
						object.setObjectName(objectSelected.getObjectName());
				}
				FacesMessage msg = new FacesMessage("Business Object Edited: ",
						((BusinessObject) event.getObject()).getObjectName());
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}

	public void onDelete(RowEditEvent event) {
		businessObjects.remove((BusinessObject) event.getObject());
		FacesMessage msg = new FacesMessage("Object Deleted");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/*Once this method has been invoked the selected business attribute has 
	 * to be identified and its attributes have to be loaded */
	public String editAttributes(BusinessObject object) {
		selectedBO = object;
//		attributes.removeAll(attributes);
//		for(BOAttribute attrib : object.getAttributes()){
//			attributes.add(attrib);
//		}
		return "editAttributes";
	}

	/***************************************************************************
	* Method:  	addAttribute
	* 
	* Purpose: 	This method takes care of adding a attribute to current business
	* 			object selected
	* 
	* Attributes: 
	* 		   	attributeName - this is the backing bean variable which contains 
	* 			Attribute name as provided by user from front end
	* 
	* 			attributeType - this is the backing bean variable which contains 
	* 			Attribute Type as provided by user from front end
	* 
	* 			businessRule - this is the backing bean variable which contains 
	* 			Business Rule as provided by user from front end
	* 			
	* 
	* checks:	1.Checks if method is invoked with empty values
	* 			2.Checks if a attribute with same name already exists
	* 
	* Notes:	1.Need to add db code
	****************************************************************************/
	public void addAttribute() {
		if (attribute.getAttributeName().equals("")||attribute.getAttributeType().equals("")||attribute.getBusinessRule().equals("")) {
			FacesMessage msg = new FacesMessage("Please enter all the input values correctly");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			boolean exists = false;
			for(BOAttribute attribute : selectedBO.attributes){
				if(attribute.getAttributeName().equalsIgnoreCase(attribute.getAttributeName()))
					exists = true;
			}
			if(exists){
				FacesMessage msg = new FacesMessage("Attribute name "+attribute.getAttributeName()+" already exists.Please choose a different name");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
//				List<BOAttribute> attributes = selectedBO.getAttributes();
				selectedBO.addAttribute(attribute.getAttributeName(), attribute.getAttributeType(),attribute.getMandatoryType(), attribute.getBusinessRule());
//				selectedBO.setAttributes(attributes);
				
				//reset all values
				attribute = new BOAttribute();
			}
		}
	}
	
	/***************************************************************************
	* Method:  	onEdit
	* 
	* Purpose: 	This method is invoked when user opts to edit a row in datatable
	* 
	* Attributes: 
	* 
	* checks:	
	* 
	* Notes:	1.Need to add db code
	****************************************************************************/
	public void onEditAttribute(RowEditEvent event) {

	}

	public void onDeleteAttribute(RowEditEvent event) {
//		attributes.remove((BOAttribute) event.getObject());
		selectedBO.attributes.remove((BOAttribute) event.getObject());
		FacesMessage msg = new FacesMessage("Attribute Deleted");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
    public String reinit() {
        setAttribute(new BOAttribute());
        return null;
    }
	
	public List<BusinessObject> getBusinessObjects() {
		return businessObjects;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	
	public BusinessObject getSelectedBO() {
		return selectedBO;
	}

	public void setSelectedBO(BusinessObject selectedBO) {
		this.selectedBO = selectedBO;
	}

	public BOAttribute getAttribute() {
		return attribute;
	}

	public void setAttribute(BOAttribute attribute) {
		this.attribute = attribute;
	}

	public String getObjectType() {
		return objectType;
		//System.out.println("test");
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public Map<String,String> getDropDownObjectTypes() {
		return dropDownObjectTypes;
	}

	public void setDropDownObjectTypes(Map<String,String> dropDownObjectTypes) {
		this.dropDownObjectTypes = dropDownObjectTypes;
	}
}