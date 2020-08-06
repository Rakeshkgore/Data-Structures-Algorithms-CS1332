public class practice {

    public static void main(String[] args) {
        char[] arr = {'d','e', 's', 'p', 'a', 'c', 'i', 't', 'o'};
        joshy(arr, arr.length-1) ;
    }

    public static void joshy(char[] arr, int i) {
        if(i < 0) {
            return;
        }
        System.out.println(arr[i]);
        joshy(arr,i-2);

        if( i - 1 >=0) {
            System.out.println(arr[i-1] + ""+ i );
        }
    }
}
