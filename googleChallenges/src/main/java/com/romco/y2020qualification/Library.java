package com.romco.y2020qualification;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private int id;
    private int numberOfBooks;
    private int signupDays;
    private int booksShippedPerDay;
    private List<String> bookList = new ArrayList<>();
    private List<String> bookShipList = new ArrayList<>();

    public Library(int id, int numberOfBooks, int signupDays, int booksShippedPerDay) {
        this.id = id;
        this.numberOfBooks = numberOfBooks;
        this.signupDays = signupDays;
        this.booksShippedPerDay = booksShippedPerDay;
    }

    public int getId() {
        return id;
    }

    public int getNumberOfBooks() {
        return numberOfBooks;
    }

    public int getSignupDays() {
        return signupDays;
    }

    public int getBooksShippedPerDay() {
        return booksShippedPerDay;
    }

    public List<String> getBookList() {
        return bookList;
    }

    @Override
    public String toString() {
        return "Library{" +
                "id=" + id +
                ", numberOfBooks=" + numberOfBooks +
                ", signupDays=" + signupDays +
                ", booksShippedPerDay=" + booksShippedPerDay +
                ", bookList=" + bookList +
                ", bookShipList=" + bookShipList +
                '}';
    }

    public void addBookToList(String bookId) {
        bookList.add(bookId);
    }

    public void addBookToShip(String bookId) {
        bookShipList.add(bookId);
    }

    public List<String> getBookShipList() {
        return bookShipList;
    }

    public void setBookShipList(List<String> bookShipList) {
        this.bookShipList = bookShipList;
    }
}
