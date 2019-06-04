```java
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int up = 0;
        ListNode result = new ListNode(0);
        ListNode tmp = result;
        while(l1!=null || l2!=null){
            int sum = (l1 != null?l1.val:0) + (l2 != null?l2.val:0) + up;
            System.out.println((l1 != null?l1.val:0)+"+"+(l2 != null?l2.val:0)+"+"+up+"="+sum);
            tmp.next = new ListNode(sum % 10);
            up = sum > 9 ? 1 : 0;
            tmp = tmp.next;
            if(l1 != null) l1 = l1.next;
            if(l2 != null) l2 = l2.next;
        }
        if(up>0) tmp.next = new ListNode(up);
        return result.next;
    }
```

