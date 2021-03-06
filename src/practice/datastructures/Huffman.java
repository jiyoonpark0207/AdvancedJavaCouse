class TreeNode {
    int weight;
    String ch;
    TreeNode right, left;
}

class element {
    TreeNode ptree;
    String ch;
    int key;
}

//public static void main()
class HeapType {
    element[] heap;
    int heap_size;

    HeapType() {
        heap = new element[200];
        heap_size = 0;
    }
}

public class Huffman {
    // 현재 요소의 개수가 heap_size인 히프 h에 item을 삽입한다.
    // 삽입 함수
    static void insert_min_heap(HeapType h, element item) {
        int i;
        i = ++(h.heap_size);

        //  트리를 거슬러 올라가면서 부모 노드와 비교하는 과정
        while ((i != 1) && (item.key < h.heap[i / 2].key)) {
            h.heap[i] = h.heap[i / 2];
            i /= 2;
        }
        h.heap[i] = item;     // 새로운 노드를 삽입
    }

    // 삭제 함수
    static element delete_min_heap(HeapType h) {
        int parent, child;
        element item = new element(), temp= new element();

        item = h.heap[1];
        temp = h.heap[(h.heap_size)--];
        parent = 1;
        child = 2;
        while (child <= h.heap_size) {
            // 현재 노드의 자식노드중 더 작은 자식노드를 찾는다.
            if ((child < h.heap_size) && (h.heap[child].key) > h.heap[child + 1].key)
                child++;
            if (temp.key < h.heap[child].key) break;
            // 한 단계 아래로 이동
            h.heap[parent] = h.heap[child];
            parent = child;
            child *= 2;
        }
        h.heap[parent] = temp;
        return item;
    }

    // 이진 트리 생성 함수
    static TreeNode make_tree(TreeNode left, TreeNode right) {
        TreeNode node = new TreeNode();
        node.left = left;
        node.right = right;
        return node;
    }

    // 이진 트리 제거 함수
    static void destroy_tree(TreeNode root) {
        if (root == null) return;
        destroy_tree(root.left);
        destroy_tree(root.right);
    }

    static boolean is_leaf(TreeNode root) {
        if(root.left == null && root.right== null)
            return true;
        else return false;

    }

    static void print_array(int codes[], int n) {
        for (int i = 0; i < n; i++)
            System.out.printf("%d", codes[i]);
        System.out.print("\n");
    }

    void print_tree(TreeNode root) {
        if (root != null) {
            System.out.printf("[%d] ", root.weight);  // 노드 방문
            print_tree(root.left);// 왼쪽서브트리 순회
            print_tree(root.right);// 오른쪽서브트리 순회
        }
    }


    static void print_codes(TreeNode root, int codes[], int top) {

        // 1을 저장하고 순환호출한다.
        if (root.left != null) {
            codes[top] = 1;
            print_codes(root.left, codes, top + 1);
        }

        // 0을 저장하고 순환호출한다.
        if (root.right != null) {
            codes[top] = 0;
            print_codes(root.right, codes, top + 1);
        }

        // 단말노드이면 코드를 출력한다.
        if (is_leaf(root)) {
            //printf("inside print \n");
            System.out.printf("%s: ", root.ch);
            //printf("inside print 2 \n");
            print_array(codes, top);
        }
    }

    // 허프만 코드 생성 함수
    static void huffman_tree(int freq[], String ch_list[], int n) {
        //printf("허프만코드 들어옴\n");
        int i;
        TreeNode node, x;
        HeapType heap = new HeapType();
        element e = new element(), e1= new element(), e2= new element();
        int codes[] = new int[0];
        int top = 0;


        for (i = 0; i < n; i++) {
            node = make_tree(null, null);
            e.ch = ch_list[i];
            node.ch = ch_list[i];
            e.key = freq[i];
            node.weight = freq[i];
            e.ptree = node;
            insert_min_heap(heap, e);
        }
        //printf("허프만코드 for 완료 \n");
        for (i = 1; i < n; i++) {
            //최소값을 가지는 두개의 노드를 삭제
            e1 = delete_min_heap(heap);
            e2 = delete_min_heap(heap);
            // 두개의 노드를 합친다.
            x = make_tree(e1.ptree, e2.ptree);
            e.key = x.weight = e1.key + e2.key;
            e.ptree = x;
            System.out.printf("%d+%d->%d \n", e1.key, e2.key, e.key);
            insert_min_heap(heap, e);
        }
        //System.out.printf("허프만코드 전부 완료 \n");

        e = delete_min_heap(heap); // 최종 트리
        //printf("허프만코드 delete 완료 \n");
        print_codes(e.ptree, codes, top);
        //System.out.printf("허프만코드 전부 완료 \n");
        destroy_tree(e.ptree);
    }
    public static void main(String[] args) {
        //char* ch_list[] = { '가' ,'각' ,'간' ,'갇' ,'갈' ,'갉' ,'감' ,'갑' ,'값' ,'갓' ,'갔' ,'강' ,
        //	'갖' ,'갗' ,'같' ,'갚' ,'갛' ,'개' ,'객' ,'갠' ,'갤' ,'갬' ,'갭' ,'갯' ,'갰' ,'갱' ,'갸' ,
        //	'갹' ,'갼' ,'걀' ,'걔' ,'걘' ,'걜' ,'거' ,'걱' ,'건' ,'걷' ,'걸' ,'검' ,'겁' ,'겂' ,'것' ,
        //	'겄' ,'겅' ,'겆' ,'겉' ,'겊' ,'겋' ,'게' ,'겐' ,'겔' ,'겜' ,'겝' ,'겟' ,'겠' ,'겡' ,'겨' ,
        //	'격' ,'겪' ,'견' ,'겯' ,'결' ,'겸' ,'겹' ,'겻' ,'겼' ,'경' ,'겿' ,'곁' ,'계' ,'곈' ,'곌' ,
        //	'곕' ,'곗' ,'고' ,'곡' ,'곤' ,'곧' ,'골' ,'곪' ,'곬' ,'곯' ,'곰' ,'곱' ,'곳' ,'공' ,'곶' ,
        //	'과' ,'곽' ,'관' ,'괄' ,'괌' ,'괍' ,'광' ,'괘' ,'괜' ,'괭' ,'괴' ,'괵' ,'괸' ,'괼' };
        String ch_list[] = {"가", "각", "간", "갇", "갈", "갉", "감", "갑", "값", "갓", "갔", "강", "갖",
                "갗", "같", "갚", "갛", "개", "객", "갠", "갤", "갬", "갭", "갯", "갰", "갱", "갸", "갹", "갼",
                "걀", "걔", "걘", "걜", "거", "걱", "건", "걷", "걸", "검", "겁", "겂", "것", "겄", "겅", "겆",
                "겉", "겊", "겋", "게", "겐", "겔", "겜", "겝", "겟", "겠", "겡", "겨", "격", "겪", "견", "겯",
                "결", "겸", "겹", "겻", "겼", "경", "겿", "곁", "계", "곈", "곌", "곕", "곗", "고", "곡", "곤",
                "곧", "골", "곪", "곬", "곯", "곰", "곱", "곳", "공", "곶", "과", "곽", "관", "괄", "괌", "괍",
                "광", "괘", "괜", "괭", "괴", "괵", "괸", "괼"};
        int freq[] = {150918, 18071, 21939, 120, 4705, 15, 11456, 2038, 846, 202, 3225, 10367, 3503,
                33, 14724, 147, 69, 20091, 1953, 28, 63, 6, 6, 90, 2, 264, 121, 2, 1, 70, 256, 17, 2,
                31282, 864, 12970, 523, 6753, 3310, 1182, 4, 58386, 96, 28, 4, 287, 31, 35, 56945, 437,
                104, 12, 9, 9, 8640, 2, 4862, 5889, 615, 3580, 2, 12551, 368, 399, 2, 1002, 25932, 1,
                416, 23653, 1, 1, 2, 11, 152862, 1968, 2105, 1594, 2712, 4, 3, 17, 681, 724, 4581, 21307,
                31, 43685, 265, 21976, 248, 6, 1, 4499, 75, 657, 34, 1348, 1, 10, 1};
        huffman_tree(freq, ch_list, 100);
    }
}
