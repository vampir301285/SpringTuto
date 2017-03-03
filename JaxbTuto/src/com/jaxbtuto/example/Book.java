package com.jaxbtuto.example;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "book")
// If you want you can define the order in which the fields are written
// Optional
public class Book {

        private String name;
        private String author;
        private String publisher;
        private String isbn;
        private float price;

        // If you like the variable name, e.g. "name", you can easily change this
        // name for your XML-Output:
        @XmlElement(name = "title")
        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getAuthor() {
                return author;
        }

        public void setAuthor(String author) {
                this.author = author;
        }

        public String getPublisher() {
                return publisher;
        }

        public void setPublisher(String publisher) {
                this.publisher = publisher;
        }

        public String getIsbn() {
                return isbn;
        }

        public void setIsbn(String isbn) {
                this.isbn = isbn;
        }

        /**
         * @return the price
         */
        @XmlAttribute
        public float getPrice() {
            return price;
        }

        /**
         * @param price the price to set
         */
        public void setPrice(float price) {
            this.price = price;
        }

}