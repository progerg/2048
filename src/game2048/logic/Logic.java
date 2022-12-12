package game2048.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Logic {
    public static ArrayList<ArrayList<Integer>> setTileWithNumberTwo(ArrayList<ArrayList<Integer>> numbers) {
        if (!hasEmptyTile(numbers)) {
            return null;
        }
        boolean found = false;
        while (!found) {
            //find random row and column to place a 2 in
            int r = (int) Math.floor(Math.random() * 4);
            int c = (int) Math.floor(Math.random() * 4);
            if (numbers.get(r).get(c) == 0) {
                numbers.get(r).set(c, 2);
                found = true;
            }
        }
        return numbers;
    }

    public static boolean hasEmptyTile(ArrayList<ArrayList<Integer>> numbers) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (numbers.get(i).get(j) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private static ArrayList<Integer> filterZero(List<Integer> row) {
        ArrayList<Integer> tiles1 = new ArrayList<>();
        for (Integer integer : row) {
            if (integer != 0) {
                tiles1.add(integer);
            }
        }
        return tiles1;
    }


    private static ArrayList<Integer> slide(ArrayList<Integer> row) {
        //[0, 2, 2, 2]
        row = filterZero(row); //[2, 2, 2]
        for (int i = 0; i < row.size() - 1; i++) {
            if (row.get(i).equals(row.get(i + 1))) {
                row.set(i, row.get(i) * 2);
                row.set(i + 1, 0);
            }
        } //[4, 0, 2]
        row = filterZero(row); //[4, 2]
        while (row.size() < 4) {
            row.add(0);
        } //[4, 2, 0, 0]
        return row;
    }

    public static ArrayList<ArrayList<Integer>> slideLeft(ArrayList<ArrayList<Integer>> numbers) {
        ArrayList<ArrayList<Integer>> numbers2 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            numbers2.add(i, slide(numbers.get(i)));
        }
        return numbers2;
    }

    public static ArrayList<ArrayList<Integer>> slideRight(ArrayList<ArrayList<Integer>> numbers) {
        ArrayList<ArrayList<Integer>> numbers2 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ArrayList<Integer> row = numbers.get(i);
            Collections.reverse(row);
            row = slide(numbers.get(i));
            Collections.reverse(row);
            numbers2.add(i, row);
        }
        return numbers2;
    }

    public static ArrayList<ArrayList<Integer>> slideUp(ArrayList<ArrayList<Integer>> numbers) {
        ArrayList<ArrayList<Integer>> numbers2 = new ArrayList<>(4);
        for (int a = 0; a < 4; a++) {
            numbers2.add(new ArrayList<>());
        }
        for (int i = 0; i < 4; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            Collections.addAll(row, numbers.get(0).get(i), numbers.get(1).get(i), numbers.get(2).get(i), numbers.get(3).get(i));
            row = slide(row);
            for (int j = 0; j < 4; j++) {
                numbers2.get(j).add(i, row.get(j));
            }
        }
        return numbers2;
    }


    public static ArrayList<ArrayList<Integer>> slideDown(ArrayList<ArrayList<Integer>> numbers) {
        ArrayList<ArrayList<Integer>> numbers2 = new ArrayList<>(4);
        for (int a = 0; a < 4; a++) {
            numbers2.add(new ArrayList<>());
        }
        for (int i = 0; i < 4; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            Collections.addAll(row, numbers.get(0).get(i), numbers.get(1).get(i), numbers.get(2).get(i), numbers.get(3).get(i));
            Collections.reverse(row);
            row = slide(row);
            Collections.reverse(row);

            for (int j = 0; j < 4; j++) {
                numbers2.get(j).add(i, row.get(j));
            }
        }
        return numbers2;
    }

//    public static Tile[][] moveRight(Tile[][] tiles) {
//        for (int i = 0; i < tiles.length; i++) {
//            for (int j = tiles[i].length - 1; j > 1; j--) {
//                if (tiles[i][j].getNumber() == 0) {
//                    tiles[i][j].setNumber(tiles[i][j - 1].getNumber());
//                    tiles[i][j - 1].setNumber(0);
//                }
//            }
//        }
//        return tiles;
//    }
//
//    public static Tile[][] moveLeft(Tile[][] tiles) {
//        for (int i = 0; i < tiles.length; i++) {
//            for (int j = 0; j < tiles.length - 1; j++) {
//                if (tiles[i][j].getNumber() == 0) {
//                    tiles[i][j].setNumber(tiles[i][j + 1].getNumber());
//                    tiles[i][j + 1].setNumber(0);
//                }
//            }
//        }
//        return tiles;
//    }
//
//    public static Tile[][] rightClicked(Tile[][] tiles) {
//        tiles = moveRight(tiles);
//        for (int y = 0; y < tiles.length; y++) {
//            for (int x = tiles[y].length - 1; x > 0; x--) {
//                if (tiles[y][x].getNumber() == tiles[y][x - 1].getNumber()) {
//                    tiles[y][x].setNumber(tiles[y][x].getNumber() * 2);
//                    if (x - 2 >= 0)
//                        tiles[y][x - 1].setNumber(tiles[y][x - 2].getNumber());
//                    else {
//                        tiles[y][x - 1].setNumber(0);
//                    }
//                }
//            }
//        }
//        tiles = moveRight(tiles);
//        System.out.println(Arrays.deepToString(tiles));
//
//        return tiles;
//    }
//
//    public static Tile[][] leftClicked(Tile[][] tiles) {
//        tiles = moveLeft(tiles);
//        for (int y = 0; y < tiles.length; y++) {
//            for (int x = 0; x < tiles[y].length - 1; x++) {
//                if (tiles[y][x].getNumber() == 256 && tiles[y][x + 1].getNumber() == 256)
//                    System.out.println(Arrays.deepToString(tiles));
//                if (tiles[y][x].getNumber() == tiles[y][x + 1].getNumber()) {
//                    tiles[y][x].setNumber(tiles[y][x].getNumber() * 2);
//                    if (x + 2 <= tiles[y].length - 1)
//                        tiles[y][x + 1].setNumber(tiles[y][x + 2].getNumber());
//                    else {
//                        tiles[y][x + 1].setNumber(0);
//                    }
//                }
//            }
//        }
//        tiles = moveLeft(tiles);
//        System.out.println(Arrays.deepToString(tiles));
//        return tiles;
//    }
}
