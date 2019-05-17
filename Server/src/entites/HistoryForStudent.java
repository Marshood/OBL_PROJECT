package entites;

import java.io.Serializable;

//Book Entity 


public class HistoryForStudent implements Serializable {
	
		private static final long serialVersionUID = 1L;
		protected String BookID;
		protected String HistoryType;
		protected String Date;
		protected String PickUpDate;
		protected String ReturnDate;
		protected String RequestedDate;
		protected String WasLate;
		protected String LostBook;
		protected String BookName;
		protected String HistoryId;
		protected String requestedbook;

		
		public String getRequestedbook() {
			return requestedbook;
		}
		public void setRequestedbook(String requestedbook) {
			this.requestedbook = requestedbook;
		}
		public String getHistoryId() {
			return HistoryId;
		}
		public void setHistoryId(String historyId) {
			HistoryId = historyId;
		}
		public String getBookName() {
			return BookName;
		}
		public void setBookName(String bookName) {
			BookName = bookName;
		}
		public String getLostBook() {
			return LostBook;
		}
		public void setLostBook(String lostBook) {
			LostBook = lostBook;
		}
		public String getBookID() {
			return BookID;
		}
		public void setBookID(String bookID) {
			BookID = bookID;
		}
		public String getHistoryType() {
			return HistoryType;
		}
		public void setHistoryType(String historyType) {
			HistoryType = historyType;
		}
		public String getDate() {
			return Date;
		}
		public void setDate(String date) {
			Date = date;
		}
		public String getWasLate() {
			return WasLate;
		}
		public void setWasLate(String wasLate) {
			WasLate = wasLate;
		}

		public String getPickUpDate() {
			return PickUpDate;
		}
		public void setPickUpDate(String pickUpDate) {
			PickUpDate = pickUpDate;
		}
		public String getRequestedDate() {
			return RequestedDate;
		}
		public void setRequestedDate(String requestedDate) {
			RequestedDate = requestedDate;
		}
		public String getReturnDate() {
			return ReturnDate;
		}
		public void setReturnDate(String returnDate) {
			ReturnDate = returnDate;
		}
	    


	
}
