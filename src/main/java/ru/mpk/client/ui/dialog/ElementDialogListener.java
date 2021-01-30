package ru.mpk.client.ui.dialog;

public interface ElementDialogListener {
    boolean okAction(AbstractDialog dialog);
    default void closed() {}
}