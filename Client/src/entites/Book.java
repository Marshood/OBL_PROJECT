package entites;

import java.io.Serializable;

//Book Entity 


public class Book implements Serializable {
	   
	public Book() { 
		super();
	}
		public String getRequested() {
		return Requested;
	}



	public void setRequested(String requested) {
		Requested = requested;
	}
		private static final long serialVersionUID = 1L;
		protected String BookID;
	    protected String BookName;
	    protected String BookAuthor;
	    protected String BookCategory; 
	    protected String EditionNumber;
	    protected String PublishDate;
	    protected String Category;
	    //protected String catlognumber;
	    protected String BookDescription;
	    protected String CopyNumber;
	    protected String PurchaseDate;
	    protected String PDF;
	    protected String RequestedCounter;
	    
	    public String getRequestedCounter() {
			return RequestedCounter;
		}
		public void setRequestedCounter(String requestedCounter) {
			RequestedCounter = requestedCounter;
		}
		public String getPDF() {
			return PDF;
		}
		public void setPDF(String pDF) {
			PDF = pDF;
		}
		public String getLoanedCopies() {
			return LoanedCopies;
		}
		public void setLoanedCopies(String loanedCopies) {
			LoanedCopies = loanedCopies;
		}
		protected String ShelfDate;
	    protected String Requested;
	    protected String LoanedCopies;
	    



		public String getBookID() {
			return BookID;
		}



		public void setBookID(String bookID) {
			BookID = bookID;
		}



		public String getBookName() {
			return BookName;
		}



		public void setBookName(String bookName) {
			BookName = bookName;
		}



		public String getBookAuthor() {
			return BookAuthor;
		}



		public void setBookAuthor(String bookAuthor) {
			BookAuthor = bookAuthor;
		}



		public String getBookCategory() {
			return BookCategory;
		}



		public void setBookCategory(String bookCategory) {
			BookCategory = bookCategory;
		}



		public String getEditionNumber() {
			return EditionNumber;
		}



		public void setEditionNumber(String editionNumber) {
			EditionNumber = editionNumber;
		}



		public String getPublishDate() {
			return PublishDate;
		}



		public void setPublishDate(String publishDate) {
			PublishDate = publishDate;
		}



		public String getCategory() {
			return Category;
		}



		public void setCategory(String category) {
			Category = category;
		}


		public String getBookDescription() {
			return BookDescription;
		}



		public void setBookDescription(String bookDescription) {
			BookDescription = bookDescription;
		}



		public String getCopyNumber() {
			return CopyNumber;
		}



		public void setCopyNumber(String copyNumber) {
			CopyNumber = copyNumber;
		}



		public String getPurchaseDate() {
			return PurchaseDate;
		}



		public void setPurchaseDate(String purchaseDate) {
			PurchaseDate = purchaseDate;
		}



		public String getShelfDate() {
			return ShelfDate;
		}



		public void setShelfDate(String shelfDate) {
			ShelfDate = shelfDate;
		}
	    


	
}
