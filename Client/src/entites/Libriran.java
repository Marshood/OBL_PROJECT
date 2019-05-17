package entites;

public abstract class Libriran extends User{
	
	protected int WorkerID;
	
	public Libriran(String name, String lastName, String email, String password,int WorkerID) {
		super(name, lastName, email, password);
		this.setWorkerID(WorkerID);
		
	}

	public int getWorkerID() {
		return WorkerID;
	}

	public void setWorkerID(int workerID) {
		WorkerID = workerID;
	}
	

}
