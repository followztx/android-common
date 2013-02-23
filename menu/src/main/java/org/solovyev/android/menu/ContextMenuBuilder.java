/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.android.menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import org.jetbrains.annotations.NotNull;
import org.solovyev.android.DialogFragmentShower;
import org.solovyev.common.BuilderWithData;

/**
 * User: serso
 * Date: 12/19/11
 * Time: 10:54 AM
 */
public class ContextMenuBuilder<T extends LabeledMenuItem<D>, D> implements BuilderWithData<DialogFragmentShower, D> {

    /*
    **********************************************************************
    *
    *                           FIELDS
    *
    **********************************************************************
    */

    @NotNull
    private final FragmentActivity fragmentActivity;

    @NotNull
    private final String fragmentTag;

    @NotNull
    private final AlertDialog.Builder menuBuilder;

    @NotNull
    private final ContextMenu<T, D> menu;

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    @NotNull
    public static <T extends Enum & LabeledMenuItem<D>, D> ContextMenuBuilder<T, D> newInstance(@NotNull FragmentActivity fragmentActivity,
                                                                                                @NotNull String fragmentTag,
                                                                                                @NotNull Class<T> enumClass) {
        return new ContextMenuBuilder<T, D>(fragmentActivity, fragmentTag, EnumContextMenu.<T, D>newInstance(enumClass));
    }

    @NotNull
    public static <T extends LabeledMenuItem<D>, D> ContextMenuBuilder<T, D> newInstance(@NotNull FragmentActivity fragmentActivity,
                                                                                         @NotNull String fragmentTag,
                                                                                         @NotNull ContextMenu<T, D> menu) {
        return new ContextMenuBuilder<T, D>(fragmentActivity, fragmentTag, menu);
    }

    private ContextMenuBuilder(@NotNull FragmentActivity fragmentActivity,
                               @NotNull String fragmentTag,
                               @NotNull ContextMenu<T, D> menu) {
        this.fragmentActivity = fragmentActivity;
        this.fragmentTag = fragmentTag;
        this.menuBuilder = new AlertDialog.Builder(fragmentActivity);
        this.menu = menu;
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @NotNull
    public AlertDialog.Builder getMenuBuilder() {
        return menuBuilder;
    }

    @NotNull
    public DialogFragmentShower build(@NotNull final D data) {
        menuBuilder.setItems(menu.getMenuCaptions(fragmentActivity), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                final LabeledMenuItem<D> menuItem = menu.itemAt(item);
                if (menuItem != null) {
                    menuItem.onClick(data, fragmentActivity);
                }
            }
        });

        return new DialogFragmentShower(fragmentActivity, fragmentTag, menuBuilder);
    }
}