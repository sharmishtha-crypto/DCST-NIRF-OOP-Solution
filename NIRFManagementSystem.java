import java.io.*;
import java.lang.reflect.Method;
import java.util.*;


enum DepartmentRole { STUDENT, TEACHING_STAFF, NON_TEACHING_STAFF }


class InvalidNirfDataException extends Exception {
    public InvalidNirfDataException(String message) {
        super(message);
    }
}


interface Reportable {
    String generateReportEntry();
}

interface Auditable {
    void auditRecord();
}

abstract class NirfEntity implements Reportable, Auditable, Cloneable {
    protected String name;
    protected String id;
    
    public NirfEntity(String name, String id) {
        this.name = name;
        this.id = id;
    }
    

    public abstract void displayRoleDetails();
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}


class PersonMember extends NirfEntity {
    private String email; 
    protected DepartmentRole role;

    public int publicationCount; 

    private static int totalMembersCreated = 0;

    public PersonMember(String name, String id, String email, DepartmentRole role, int pubCount) {
        super(name, id);
        this.email = email;
        this.role = role;
        this.publicationCount = pubCount;
        totalMembersCreated++;
    }


    public static void displayMemberCount() {
        System.out.println("[STATIC] Total DCST/GBS Members Registered across 3 years: " + totalMembersCreated);
    }

   
    public void demonstrateAccessModifiers() {
        System.out.println("  - Accessing Private Email inside class: " + this.email);
        System.out.println("  - Accessing Protected Role inside class: " + this.role);
        System.out.println("  - Accessing Public Publications inside class: " + this.publicationCount);
    }

    @Override
    public void displayRoleDetails() {
        System.out.println("Role: " + role + " | Name: " + name + " | ID: " + id);
    }

    @Override
    public String generateReportEntry() {
        return id + "," + name + "," + role + "," + publicationCount;
    }

    @Override
    public void auditRecord() {
        System.out.println("Auditing NIRF record for ID: " + id + " [" + name + "]");
    }

   
    public PersonMember shallowClone() throws CloneNotSupportedException {
        return (PersonMember) super.clone();
    }

    public PersonMember deepClone() {
        return new PersonMember(new String(this.name), new String(this.id), new String(this.email), this.role, this.publicationCount);
    }
}


class ActivityEvaluator {

    public void evaluateActivity(String activityName) {
        System.out.println("Evaluating Activity [Standard]: " + activityName);
    }

    public void evaluateActivity(String activityName, int score) {
        System.out.println("Evaluating Activity [Scored]: " + activityName + " with NIRF weight score: " + score);
    }
}


class NirfRepository<T> {
    private List<T> items = new ArrayList<>();

    public void addItem(T item) {
        items.add(item);
    }

    public List<T> getItems() {
        return items;
    }

    public void displayAllItems() {
        for (T item : items) {
            System.out.println("   -> " + item.toString());
        }
    }
}

public class NIRFManagementSystem {


    public static void processReport(Reportable reportableObj) {
        System.out.println("Processing Message Passing Report -> " + reportableObj.generateReportEntry());
    }


    public static void validatePubCount(int count) throws InvalidNirfDataException {
        if (count < 0) {
            throw new InvalidNirfDataException("Publication/Activity count cannot be negative!");
        }
        System.out.println("Validation successful for count: " + count);
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" DCST, GOA UNIVERSITY - NIRF 3-YEAR DATA SYSTEM ");
        System.out.println("==================================================\n");

        System.out.println("--- 1. Object Referencing & Constructors ---");
        PersonMember member1 = new PersonMember("Sharmishtha", "DCST2648", "sharmishtha@unigoa.ac.in", DepartmentRole.STUDENT, 5);
        PersonMember memberRef = member1; 
        memberRef.setName("Sharmishtha S.");
        System.out.println("Original object name after reference modification: " + member1.getName());
        System.out.println();

      
        System.out.println("--- 2. Static Attributes & Methods ---");
        PersonMember member2 = new PersonMember("Dr. Sadhvi Manerikar", "FAC01", "sadhvi@unigoa.ac.in", DepartmentRole.TEACHING_STAFF, 25);
        PersonMember member3 = new PersonMember("Hanumant Redkar", "FAC02", "hanumant@unigoa.ac.in", DepartmentRole.TEACHING_STAFF, 18);
        PersonMember.displayMemberCount();
        System.out.println();

        System.out.println("--- 4. Access Modifiers Demonstration ---");
        member1.demonstrateAccessModifiers();
   
        System.out.println("  - Accessing Public field directly: " + member1.publicationCount);
        System.out.println();

        
        System.out.println("--- 5. Abstract Class & Interface Implementation ---");
        member1.displayRoleDetails();
        member2.displayRoleDetails();
        System.out.println();

        
        System.out.println("--- 6. Class as a Type & Message Passing ---");
        processReport(member1);
        processReport(member2);
        System.out.println();

     
        System.out.println("--- 7. Polymorphism (Overloading & Overriding) ---");
        ActivityEvaluator evaluator = new ActivityEvaluator();
        evaluator.evaluateActivity("InfoFest Hackathon");
        evaluator.evaluateActivity("SEA Semester Exams", 95);
        System.out.println();

        
        System.out.println("--- 8. Generics & Collection Frameworks ---");
        NirfRepository<PersonMember> repo = new NirfRepository<>();
        repo.addItem(member1);
        repo.addItem(member2);
        repo.addItem(member3);

 
        List<String> academicActivities = Arrays.asList("ISA", "Midpoint", "SEA", "Study-Tour", "Placements");
        Set<String> nonAcademicActivities = new HashSet<>(Arrays.asList("InfoFest", "Plateaunia", "Play-on", "Hackathon"));
        Queue<String> taskQueue = new LinkedList<>(Arrays.asList("Verify Student Data", "Compile Faculty Publications", "Submit NIRF Portal"));
        Map<String, String> courseMap = new HashMap<>();
        courseMap.put("MCA", "Master of Computer Applications");
        courseMap.put("MSC", "M.Sc. Computer Science");
        courseMap.put("PHD", "Ph.D. Computer Science & Technology");

        System.out.println("Academic Activities List: " + academicActivities);
        System.out.println("Non-Academic Activities Set: " + nonAcademicActivities);
        System.out.println("Pending NIRF Task Queue: " + taskQueue);
        System.out.println("DCST Program Map: " + courseMap);
        System.out.println();


        System.out.println("--- 9. Exception Handling ---");
        try {
            validatePubCount(10);
            validatePubCount(-2); 

        } catch (InvalidNirfDataException e) {
            System.out.println("[CAUGHT CUSTOM EXCEPTION]: " + e.getMessage());
        } finally {
            System.out.println("[FINALLY BLOCK]: Exception safety check completed.");
        }
        System.out.println();

       
        System.out.println("--- 10. Reflection, Persistence, I/O & Cloning ---");
        
        
        try {
            Class<?> c = Class.forName("PersonMember");
            System.out.println("Reflection - Class Name: " + c.getName());
            System.out.println("Reflection - Declared Methods count: " + c.getDeclaredMethods().length);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String fileName = "nirf_summary_dcst.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("NIRF Submission Data - DCST, Goa University\n");
            writer.write(member1.generateReportEntry() + "\n");
            writer.write(member2.generateReportEntry() + "\n");
            System.out.println("Persistence I/O: Data successfully written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            PersonMember clonedShallow = member1.shallowClone();
            PersonMember clonedDeep = member1.deepClone();
            System.out.println("Shallow Clone Name: " + clonedShallow.getName());
            System.out.println("Deep Clone Name: " + clonedDeep.getName());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        System.out.println("\n==================================================");
        System.out.println(" NIRF OOP SOLUTION EXECUTION COMPLETED SUCCESSFULLY ");
        System.out.println("==================================================");
    }
}