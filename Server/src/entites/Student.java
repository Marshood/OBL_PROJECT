package entites;

public abstract class Student extends User{
	
	protected int CardId;
	public Student(String name, String lastName, String email, String password, int CardID, int CardId) {
		super(name, lastName, email, password);
		this.setCardId(CardId);
	}
	public int getCardId() {
		return CardId;
	}
	public void setCardId(int cardId) {
		CardId = cardId;
	}
	

}
