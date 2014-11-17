package art.datastructures;

import java.io.Serializable;

public class BOAttribute implements Serializable {
	private int attributeID;
	private String attributeName;
	private String attributeType;
	private String businessRule;
	private String mandatoryType;

	public BOAttribute() {
		mandatoryType = "NA";
	}

	public BOAttribute(int attributeID, String attributeName,
			String attributeType, String mandatoryType, String businessRule) {
		this.attributeID = attributeID;
		this.attributeName = attributeName;
		this.attributeType = attributeType;
		this.mandatoryType = mandatoryType;
		this.businessRule = businessRule;
	}

	public boolean equals(Object attribute) {
		if (this.attributeName.equals(((BOAttribute) attribute)
				.getAttributeName()))
			return true;
		else
			return false;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	public String getBusinessRule() {
		return businessRule;
	}

	public void setBusinessRule(String businessRule) {
		this.businessRule = businessRule;
	}

	public int getAttributeID() {
		return attributeID;
	}

	public void setAttributeID(int attributeID) {
		this.attributeID = attributeID;
	}

	public String getMandatoryType() {
		return mandatoryType;
	}

	public void setMandatoryType(String isMandatoryType) {
		this.mandatoryType = isMandatoryType;
	}
}
