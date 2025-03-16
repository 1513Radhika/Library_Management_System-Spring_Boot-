package com.jsp.springboot.library.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "borrowed_book_id", nullable = false) // One fine per borrowed book
	private BorrowedBook borrowedBook;

	@Column(nullable = false)
	private Double amount;

	@Column(nullable = false)
	private boolean isPaid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BorrowedBook getBorrowedBook() {
		return borrowedBook;
	}

	public void setBorrowedBook(BorrowedBook borrowedBook) {
		this.borrowedBook = borrowedBook;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

}
