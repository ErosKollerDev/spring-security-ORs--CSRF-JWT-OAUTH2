package com.eazybytes.springsecsection1.test_ai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String title;
    private String author;
    private  static   List<Book> books = new  ArrayList<>();

    static {
//        books.add(Book.builder().title("Book1").author("Author1").build());
//        books.add(Book.builder().title("Book2").author("Author2").build());
//        books.add(Book.builder().title("Book3").author("Author3").build());
    }


    public Book createBook(String title, String author) {
        Book newBook = Book.builder().title(title).author(author).build();
        this.books.add(newBook);
        return newBook;
    }

    public List<Book> readBooks() {
        return this.books;
    }

    public Book updateBook(int index, String title, String author) {
        if (index < this.books.size()) {
            Book book = this.books.get(index);
            if (title != null) {
                book.setTitle(title);
            }
            if (author != null) {
                book.setAuthor(author);
            }
            return book;
        } else {
            return null;
        }
    }

    public boolean deleteBook(int index) {
        if (index < this.books.size()) {
            this.books.remove(index);
            return true;
        } else {
            return false;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * This method can perform arithmetic operations on two numbers
     *
     * @param op   The operation to perform on the two numbers. Valid options are:
     *             "add", "sub", "mul", "div", "rem"
     * @param num1 The first number
     * @param num2 The second number
     * @return The result of the operation
     */
    public static double performOperation(String op, double num1, double num2) {
        double result = 0;
        switch (op) {
            case "add":
                result = num1 + num2;
                break;
            case "sub":
                result = num1 - num2;
                break;
            case "mul":
                result = num1 * num2;
                break;
            case "div":
                if (num2 == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                result = num1 / num2;
                break;
            case "rem":
                if (num2 == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                result = num1 % num2;
                break;
            default:
                throw new IllegalArgumentException("Invalid operation: " + op);
        }
        return result;
    }

    // Example usage:
    public static void main(String[] args) {
        Book book = Book.builder().title("Book1").author("Author1").build();
        System.out.println(book.createBook("Book2", "Author2")); // Create
        System.out.println(book.readBooks()); // Read
        System.out.println(book.updateBook(0, "New Title", null)); // Update
        System.out.println(book.deleteBook(0)); // Delete
    }
}
