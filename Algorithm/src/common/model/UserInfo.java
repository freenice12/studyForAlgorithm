package common.model;

import java.io.Serializable;
import java.util.UUID;

public class UserInfo implements Serializable {
	
	private static final long serialVersionUID = -3456939848314935619L;

	private UUID uuid ;
	private String name;
	private boolean isReady;
	private boolean isPass;
	private boolean isLoser;
	
	public UserInfo(UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
		this.isReady = false;
		this.isPass = false;
		this.isLoser = false;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isReady() {
		return isReady;
	}
	
	public boolean isPass() {
		return isPass;
	}
	
	public boolean isLoser() {
		return isLoser;
	}
	
	public void setLoser(boolean isLoser) {
		this.isLoser = isLoser;
	}
	
	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}
	
	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}
}