import java.util.*;

public class HuffmanCoding {

    public Map<Character, Integer> buildFrequencyTable(String input){
        Map<Character, Integer> frequencyTable = new HashMap<>();
        for(char ch : input.toCharArray()){
            frequencyTable.put(ch, frequencyTable.getOrDefault(ch, 0) + 1);
        }
        return frequencyTable;
    }

    public Node buildHuffmanTree(Map<Character, Integer> frequencyTable){
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.freq));
        for(Map.Entry<Character, Integer> entry : frequencyTable.entrySet()){
            priorityQueue.add(new Node(entry.getKey(), entry.getValue()));
        }
        while (priorityQueue.size() > 1){
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();
            Node merged = new Node('\0', left.freq + right.freq, left, right);
            priorityQueue.add(merged);
        }
        return priorityQueue.poll();
    }

    public void generateCodes(Node node, String code, Map<Character, String> huffmanCode){
        if(node == null){
            return;
        }
        if(node.left == null && node.right == null){
            huffmanCode.put(node.ch, code);
        }
        generateCodes(node.left, code + "0", huffmanCode);
        generateCodes(node.right, code + "1", huffmanCode);
    }

    public String encode(String input, Map<Character, String> huffmanCode){
        StringBuilder encodedString = new StringBuilder();
        for(char ch : input.toCharArray()){
            encodedString.append(huffmanCode.get(ch));
        }
        return encodedString.toString();
    }

    public String decode(String encodedString, Node root){
        StringBuilder decodedString = new StringBuilder();
        Node currentNode = root;
        for(char bit: encodedString.toCharArray()){
            currentNode = bit == '0' ? currentNode.left : currentNode.right;
            if(currentNode.left == null && currentNode.right == null){
                decodedString.append(currentNode.ch);
                currentNode = root;
            }
        }
        return  decodedString.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ASCII-encoded text:");
        String input = scanner.nextLine();

        HuffmanCoding huffman = new HuffmanCoding();
        Map<Character, Integer> frequencyTable = huffman.buildFrequencyTable(input);
        Node root = huffman.buildHuffmanTree(frequencyTable);
        Map<Character, String> huffmanCode = new HashMap<>();
        huffman.generateCodes(root, "", huffmanCode);

        System.out.println("Generated Huffman Codes:");
        for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        String encodedString = huffman.encode(input, huffmanCode);
        System.out.println("Encoded String: " + encodedString);

        String decodedString = huffman.decode(encodedString, root);
        System.out.println("Decoded String: " + decodedString);
    }
}
