package me.bionicbeanie.mods.savecoords.gui.impl;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import me.bionicbeanie.mods.savecoords.IDimensionAware;
import me.bionicbeanie.mods.savecoords.IDimensionAware.IDimension;
import me.bionicbeanie.mods.savecoords.model.PlayerRawPosition;
import net.minecraft.text.LiteralText;

public class DimensionButton extends WButton {

    private CircularList<IDimension> allDimensions;

    public DimensionButton(WSprite sprite, IDimensionAware dimensionAware, PlayerRawPosition existingPosition) {
        allDimensions = new CircularList<IDimension>();
        for (IDimension dimension : dimensionAware.getDimensions()) {
            allDimensions.add(dimension);
        }
        
        IDimension existingDimension = dimensionAware.getCurrentDimension();
        if(existingPosition != null) {
            existingDimension = dimensionAware.getDimension(existingPosition.getWorldDimension());
        }
        while(allDimensions.next() != existingDimension);
        
        IDimension currentDimension = allDimensions.current();
        
        sprite.setImage(currentDimension.getSpriteIdentifier());
        setLabel(new LiteralText(currentDimension.getName()));

        setOnClick(() -> {
            allDimensions.next();
            IDimension current = getDimension();
            sprite.setImage(current.getSpriteIdentifier());
            setLabel(new LiteralText(current.getName()));
        });
    }

    public IDimension getDimension() {
        return allDimensions.current();
    }
}

// TODO : Probably move ad-hoc circular list elsewhere

class Node<T> {
    T value;
    Node<T> next;

    Node(T t) {
        this.value = t;
    }
}

class CircularList<T> {
    Node<T> head = null;
    Node<T> tail = null;
    Node<T> current = null;

    void add(T t) {
        if (head == null) {
            head = new Node<T>(t);
            tail = head;
            head.next = tail;
            tail.next = head;
            return;
        }

        Node<T> temp = new Node<T>(t);
        temp.next = head;
        tail.next = temp;
        tail = temp;
    }

    T next() {
        if (current == null) {
            current = head;
        } else {
            current = current.next;
        }

        return current.value;
    }

    T current() {
        if (current == null) {
            return null;
        }
        return current.value;
    }
}
