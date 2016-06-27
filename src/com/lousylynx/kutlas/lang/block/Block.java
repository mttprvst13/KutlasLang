package com.lousylynx.kutlas.lang.block;

import java.util.ArrayList;

public abstract class Block
{

    private Block superBlock;
    private ArrayList<Block> subBlocks;

    public Block(Block superBlock)
    {
        this.superBlock = superBlock;
        this.subBlocks = new ArrayList<>();
    }

    public Block getSuperBlock()
    {
        return superBlock;
    }

    public void addBlock(Block block)
    {
        subBlocks.add(block);
    }

    public abstract void run();
}
