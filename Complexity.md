n is the length of input array or string

Question 1:
 O(nlog(n))
 
Question2:
 O(n)
 
Question3:
 O(n)
 
Question4:
 O(n**2) 
 
Question5:
 n is the input, consider we check prime numbers first for 2-n, each time used the number of prime numbers smaller than suqareroot of current number.
 that's around O(nlog(n^0.5)) and the dfs cost about 2^(log(n))=n;
 the final resule is O(nlogn)
 

ps. the description of question 2 is not very clear.
