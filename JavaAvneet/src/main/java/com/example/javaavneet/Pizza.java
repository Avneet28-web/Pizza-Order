package com.example.javaavneet;

public class Pizza {

        private int orderId;
        private String cname;
        private String mob;
        private String size;
        private String toppings;
        private double total;

        public Pizza(int orderId, String cname, String mob, String size, String toppings, double total) {
            this.orderId = orderId;
            this.cname = cname;
            this.mob = mob;
            this.size = size;
            this.toppings = toppings;
            this.total = total;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getMob() {
            return mob;
        }

        public void setMob(String mob) {
            this.mob = mob;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getToppings() {
            return toppings;
        }

        public void setToppings(String toppings) {
            this.toppings = toppings;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }
    }

