package entites;

public abstract class Manager extends User{
		
	protected int WorkerID;
		
		public Manager(String name, String lastName, String email, String password,int WorkerID) {
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

