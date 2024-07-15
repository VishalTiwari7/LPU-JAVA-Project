import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class ElectricityBillingApplication {
    private static List<Customer> customers = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Electricity Billing Application");
            System.out.println("1. Create New Customer");
            System.out.println("2. View Customer Bills");
            System.out.println("3. Pay Bill");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createNewCustomer(scanner);
                    break;
                case 2:
                    viewCustomerBills(scanner);
                    break;
                case 3:
                    payBill(scanner);
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createNewCustomer(Scanner scanner) {
        System.out.print("Enter customer name: ");
        String customerName = scanner.next();
        System.out.print("Enter units consumed: ");
        int unitsConsumed = scanner.nextInt();

        Customer customer = new Customer(customerName, unitsConsumed);
        customers.add(customer);

        System.out.println("Customer created successfully!");
    }

    private static void viewCustomerBills(Scanner scanner) {
        System.out.print("Enter customer name: ");
        String customerName = scanner.next();

        Customer customer = findCustomer(customerName);
        if (customer!= null) {
            System.out.println("Bills for " + customer.getName());
            for (Bill bill : customer.getBills()) {
                System.out.println("Bill Date: " + bill.getDate());
                System.out.println("Units Consumed: " + bill.getUnitsConsumed());
                System.out.println("Bill Amount: Rs." + String.format("%.2f", bill.getBillAmount()));
                System.out.println();
            }
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void payBill(Scanner scanner) {
        System.out.print("Enter customer name: ");
        String customerName = scanner.next();
        System.out.print("Enter bill date (yyyy-mm-dd): ");
        String billDate = scanner.next();

        Customer customer = findCustomer(customerName);
        if (customer!= null) {
            Bill bill = customer.findBill(billDate);
            if (bill!= null) {
                System.out.println("Bill paid successfully!");
                bill.setPaid(true);
            } else {
                System.out.println("Bill not found.");
            }
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static Customer findCustomer(String customerName) {
        for (Customer customer : customers) {
            if (customer.getName().equals(customerName)) {
                return customer;
            }
        }
        return null;
    }
}

class Customer {
    private String name;
    private List<Bill> bills;

    public Customer(String name, int unitsConsumed) {
        this.name = name;
        this.bills = new ArrayList<>();
        this.bills.add(new Bill(unitsConsumed));
    }

    public String getName() {
        return name;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public Bill findBill(String billDate) {
        for (Bill bill : bills) {
            if (bill.getDate().equals(billDate)) {
                return bill;
            }
        }
        return null;
    }
}

class Bill {
    private String date;
    private int unitsConsumed;
    private double billAmount;
    private boolean paid;

    public Bill(int unitsConsumed) {
        this.date = java.time.LocalDate.now().toString();
        this.unitsConsumed = unitsConsumed;
        this.billAmount = calculateBillAmount(unitsConsumed);
        this.paid = false;
    }

    public String getDate() {
        return date;
    }

    public int getUnitsConsumed() {
        return unitsConsumed;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    private double calculateBillAmount(int unitsConsumed) {
        if (unitsConsumed <= 100) {
            return unitsConsumed * 9.00;
        } else if (unitsConsumed <= 200) {
            return 100 * 0.20 + (unitsConsumed - 100) * 10.50;
        } else {
                return 100 * 0.20 + 100 * 0.30 + (unitsConsumed - 200) * 13.00;
        }
    }
}