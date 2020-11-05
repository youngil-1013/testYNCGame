package com.example.bomberkong.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.bomberkong.R;
import com.example.bomberkong.util.Int2;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the Food a Player Object can pick up. The respective player's point increases by 1 when the food is picked up.
 */
public class Food {
    public static Int2 position;
    private int cellWidth;
    private int cellHeight;

    // size of pixels of food: which is equal to a single block on the grid
    private Bitmap mBitmapFood;

    public Food(Context context, Int2 position, Int2 cellSize) {
        this.position = position;
        cellWidth = cellSize.x;
        cellHeight = cellSize.y;

        // we can spawn food, and then move it around everytime player eats it.
        // todo: if needed, we can refactor this to spawn more and more instances of food over time in an arraylist. (complex)
        mBitmapFood =
                BitmapFactory.decodeResource(context.getResources(), R.drawable.banana);
        // Resize the bitmap
        // todo: gridPosToAbsolute
        mBitmapFood =
                Bitmap.createScaledBitmap(mBitmapFood, cellWidth, cellHeight, false);
    }

    /**
     * spawn takes a list of all candidates and generates a new position for the food.
     * @param empty (ArrayList containing all empty spaces)
     * @param spawnRangeX (int, size of Grid)
     * @param spawnRangeY (int, size of Grid)
     * @return location New location of fruit
     */

    public Int2 spawn(ArrayList<Int2> empty, int spawnRangeX, int spawnRangeY) {
        // Choose two random values, validate that its empty, then place the food
        Random random = new Random();

        int candidateX = random.nextInt(spawnRangeX) + 1;
        int candidateY = random.nextInt(spawnRangeY) + 1;

        // todo: while we haven't gotten a good candidate, keep running and find a new area to place the apple
        while (!(empty.contains(new Int2(candidateX, candidateY)))) {
            candidateX = random.nextInt(spawnRangeX) + 1;
            candidateY = random.nextInt(spawnRangeY) + 1;
        }
        // if an empty cell is found:
        position.x = candidateX;
        position.y = candidateY;

        return position;
    }

    // allow World to know where the food is
    public Int2 getLocation() {
        return position;
    }

    public void setLocation(Int2 location) { position = location; }

    // the game objects will handle drawing themselves
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmapFood,position.getX() * cellWidth, position.getY() * cellHeight, paint);
    }
}

