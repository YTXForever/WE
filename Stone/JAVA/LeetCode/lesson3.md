```java
//待优化    
public static int lengthOfLongestSubstring(String s) {
        int length = s.length();
        if(length < 2) return length;
        int result = 0;
        int tmp = 0;
        int start = 0;
        for(int i = 0;i<length;i++){
            String str = String.valueOf(s.charAt(i));
            if(s.substring(start,i).contains(str)) {
                tmp = i - start;
                start = s.substring(0,i).lastIndexOf(str) + 1;
                if(tmp>result) result = tmp;
                tmp = i - start + 1;
            } else {
                tmp++;
            }
            //System.out.print(s.substring(start,i)+" ");
            //System.out.println(s.charAt(i)+" tmp:"+tmp+" result:"+result);
        }
        if(tmp>result) result = tmp;
        //System.out.println(result);
        return result;
    }
```

