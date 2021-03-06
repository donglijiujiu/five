/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ez.layout.formlayout.core;

import java.awt.Component;

/**
 *
 * @author Justis.Ren
 */
public final class FormAttachment {

    int numerator;
    public static final int denominator = 100;
    int offset;
    Component component;
    int alignment;

    public FormAttachment(int numerator) {
        this(numerator, 0);
    }

    public FormAttachment(int numerator, int offset) {
        this.numerator = numerator;
        this.offset = offset;
    }

    public FormAttachment(Component component) {
        this(component, 0);
    }

    public FormAttachment(Component component, int offset) {
    	this(component, offset, Alignment.DEFAULT);
    }

    public FormAttachment(Component component, int offset, int alignment) {
        this.component = component;
        this.offset = offset;
        this.alignment = alignment;
    }

    FormAttachment minus(int value) {
        return new FormAttachment(numerator, offset - value);
    }

    FormAttachment plus(int value) {
        return new FormAttachment(numerator, offset + value);
    }

    int solveX(int value) {
        return ((numerator * value) / denominator) + offset;
    }

    int solveY(int value) {
        return (value - offset) * denominator / numerator;
    }

    @Override
    public String toString() {
        return "FormAttachment [numerator=" + numerator + ", offset=" + offset
                + ", component=" + (component == null ? component : component.getName()) + "]";
    }
}
