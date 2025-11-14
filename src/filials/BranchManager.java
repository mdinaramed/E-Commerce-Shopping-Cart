package filials;
import java.util.*;

public class BranchManager {
    private final Map<BranchType, Branch> branches = new HashMap<>();

    public BranchManager(){
        for(BranchType type : BranchType.values()){
            branches.put(type, new Branch(type));
        }
    }
    public Branch getBranch(BranchType type){
        return branches.get(type);
    }

    public void showAllBranches(){
        System.out.println("All branches:");
        for(Branch branch : branches.values()){
            System.out.println(" - " + branch);
        }
    }
}
