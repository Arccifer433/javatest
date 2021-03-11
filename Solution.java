import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Solution
 */
public class Solution {
 public static void main(String[] args) {
     if(Tests.test1()){
        System.out.println("question 1 passed tests");
     }
     if(Tests.test3()){
        System.out.println("question 3 passed tests");
     }
     if(Tests.test4()){
        System.out.println("question 4 passed tests");
     }
     if(Tests.test5()){
        System.out.println("question 5 passed tests");
     }
    //  Question 2 testing
     List<Integer> question2inp = Arrays.asList(new Integer[] {1, 3, 5, 2, 4, 6, 8, 2});
     Question2.distantODDNumbers(question2inp);
     System.out.println(question2inp);
    }


 }

class Question1{
   public static List<Integer> funnySort(List<Integer> unsorted) {
       List<Integer> result = new ArrayList<>();
       Node head = new Node(0);
       Node cur = head;
       // push all value to linked list
       for(int i: unsorted){
           cur.next = new Node(i);
           cur = cur.next;
       }
       // merge sort
       head.next = mergeSort(head.next);
       // push the sorted result to return list
       cur = head.next;
       while(cur!=null){
           result.add(cur.val);
           cur = cur.next;
       }
    //    System.out.println(result);
       List<Integer> res = new ArrayList<>();
       int left=0, right=result.size()-1;
       boolean dir = true;
       while(left<=right){
           if(dir){
               res.add(result.get(right));
               right--;
               if(left<=right){
                   res.add(result.get(right));
                   right--;
               }
               dir = !dir;
           }
           else{
               res.add(result.get(left));
               left++;
               if(left<=right){
                   res.add(result.get(left));
                   left++;
               }
               dir = !dir;
           }
       }
       return res;
   }

   static Node merge(Node l1, Node l2){
       Node res = new Node(0);
       Node cur = res;
       while(l1!=null && l2!=null){
           if(l1.val<l2.val){
               cur.next = l1;
               l1 = l1.next;
           }
           else{
               cur.next = l2;
               l2 = l2.next;
           }
           cur = cur.next;
       }
       if(l1!=null){
           cur.next = l1;
       }
       else{
           cur.next = l2;
       }
       return res.next;
   }

   static Node mergeSort(Node head){
       if(head == null)
           return null;
       Node fast = head.next;
       Node slow = head;
       while(fast!=null && fast.next!=null){
           slow = slow.next;
           fast = fast.next.next;
       }
       // only one node
       if(slow.next == null){
           return head;
       }
       Node half = slow.next;
       slow.next = null;
    //    System.out.println("sort "+ head.val + ", " + half.val);
       return merge(mergeSort(head), mergeSort(half));
   }

}

class Node{
   int val;
   Node next;
   Node(int val){
       this.val = val;
       this.next = null;
   }
   Node(int val, Node next){
       this.val = val;
       this.next = next;
   }
}

class Question2{
    static List<Integer> distantODDNumbers(List<Integer> input) { 
        /* return the same original array (with the modification) that is passed  to the method*/ 
        int oddnum=0, evennum=0;
        for( int i : input){
            if( i%2==0){
                evennum++;
            }
            else{
                oddnum++;
            }
        }
        if (evennum<oddnum-1||oddnum<2){
            return input;
        }
        int interval = (int)(evennum/(oddnum-1));
        int p1=0, p2=0;
        int temp;
        while(p1<input.size()){
            if(p1%(interval+1)!=0){
                if(input.get(p1)%2==1){
                    while(input.get(p2)%2==1){
                        p2+=interval+1;
                    }
                    temp = input.get(p1);
                    input.set(p1, input.get(p2));
                    input.set(p2, temp);
                }
            }
            p1++;
        }
        return input;
    }
}

class Question3{
    static boolean isAnagram(String a, String b, int x){
        return isAnagramhelper(a, b, x) || isAnagramhelper(a, b, -x);
    }
    static boolean isAnagramhelper(String a, String b, int x) {
        int[] freq = new int[26];
        countFreq(a,freq,1, x);
        countFreq(b,freq, -1,0);
        for(int i=0;i<26;++i){
            if(freq[i]!=0){
                return false;
            }
        }
        return true;
    }
    private static void countFreq(String target,int[] freq,int step,int shift){
        if(freq.length<26)
            return;
        for(int i=0;i<target.length();++i){
            int pos =shift(target.charAt(i)-'a',shift);
            freq[pos] += step;
        }
    }
    private static int shift(int original,int x){
        original = original + x;
        while(original<0){
            original+=26;
        }
        return original % 26;
    }
}

class Question4{
    static List<Integer> highestSumList(List<List<Integer>> lst) {
        for( int i=lst.size()-2; i>=0;i--){
            for(int j = 0; j<=i; j++){
                lst.get(i).set(j, lst.get(i+1).get(j)>lst.get(i+1).get(j+1)?lst.get(i+1).get(j)+lst.get(i).get(j):lst.get(i+1).get(j+1)+lst.get(i).get(j));
            }
        }
        List<Integer> res= new ArrayList<>();
        int j = 0;
        int newj = 0;
        for( int i=0; i<lst.size()-1;i++){
            newj = lst.get(i+1).get(j)>lst.get(i+1).get(j+1)?j:j+1;
            res.add(lst.get(i).get(j)-lst.get(i+1).get(newj));
            j = newj;
        }
        res.add(lst.get(lst.size()-1).get(newj));
        return res;
    } 
}

class Question5{
    private static List<Integer> primesList = new ArrayList<>();
    private static Set<Integer> primes = new HashSet<>();
    private static int currentMax = 1;
    private static boolean checkPrime(int number){
        if(primes.contains(number)) return true;
        if(number<=currentMax) return false;
        synchronized (Question5.class) {
            for (int i = currentMax+1 ; i <= number; ++i) {
                if (calculatePrime(i)) {
                    primesList.add(i);
                    primes.add(i);
                }
                currentMax = i;
            }
        }
        return primes.contains(number);
    }
    private static boolean calculatePrime(int number){
        for(int i=0;i<primesList.size() && primesList.get(i)*primesList.get(i)<=number ;++i){
            if(number%primesList.get(i)==0)
                return false;
        }
        return true;
    }
    public static List<Integer> sumOfPrimes(int number){
        List<Integer> res = new ArrayList<>();
        if(checkPrime(number)){
            res.add(number);
            return res;
        }
        checkSum(number,res,0);
        return res;
    }
    private static boolean checkSum(int number, List<Integer> res, int beginAt){
        if(number<2 || beginAt>=primesList.size())
            return false;
        if(primesList.get(beginAt) == number){
            res.add(number);
            return true;
        }
        int nextPrime = primesList.get(beginAt);
        if(checkSum(number,res,beginAt+1)){
            return true;
        }
        res.add(nextPrime);
        if(checkSum(number-nextPrime,res,beginAt+1)){
            return true;
        }
        res.remove(res.size()-1);
        return false;
    }
}

class Tests{
    static boolean test1(){
        Integer[] unsorted = {1, 7, 2, 3, 19, 5};
        List<Integer> list = Question1.funnySort(Arrays.asList(unsorted));
        //System.out.println(list.size());
        // System.out.println(laist);
        if(!list.equals(Arrays.asList(new Integer[] {19, 7, 1, 2, 5, 3}))){
            return false;
        }
        return true;
    }

    static boolean test3(){
        if(!Question3.isAnagram("zzcz", "aaad", -1)){
            return false;
        }
        if(!Question3.isAnagram("aaad", "zzcz", -1)){
            return false;
        }
        return true;
    }

    static boolean test4(){
        Integer[] row1 = {1, 0, 0, 0};
        Integer[] row2 = {2, 5, 0, 0};
        Integer[] row3 = {3, 2, 1, 0};
        Integer[] row4 = {1, 3, 2, 1};
        List[] rows = {Arrays.asList(row1), Arrays.asList(row2), Arrays.asList(row3), Arrays.asList(row4)};
        List<List<Integer>> lst = Arrays.asList(rows);
        if (!Question4.highestSumList(lst).equals(Arrays.asList(new Integer[] {1, 5, 2, 3}))){
            return false;
        }
        return true;
    }

    static boolean test5(){
        if (!Question5.sumOfPrimes(6).equals(Arrays.asList(new Integer[] {}))){
            return false;
        }
        List<Integer> sum9 = Question5.sumOfPrimes(9);
        if (!Tests.checkQuestion5(sum9, 9)){
            return false;
        }
        if (!Tests.checkQuestion5(Question5.sumOfPrimes(5), 5)){
            return false;
        }
        return true;
    }

    static boolean checkQuestion5(List<Integer> lst, int sum){
        for(int i:lst){
            if(i<2){
                return false;
            }
            sum-=i;
        }
        if (sum!=0){
            return false;
        }
        Set<Integer> targetSet = new HashSet<Integer>(lst);
        if(targetSet.size()<lst.size()){
            return false;
        }
        for (int i : lst){
            for (int j = 2; j <= Math.sqrt(i); j++) {
                // condition for nonprime number
                if (i % j == 0) {
                  return false;
                }
              }
        }
        return true;
    }
}
