package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.view.View;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.OneTimeAttrProcess;
import org.itsnat.droid.impl.xmlinflater.layout.OneTimeAttrProcessChildGridLayout;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescView;
import org.itsnat.droid.impl.xmlinflater.layout.attr.GridLayout_rowSpec;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_layout_row extends AttrDescView
{
    public AttrDescView_view_View_layout_row(ClassDescViewBased parent)
    {
        super(parent,"layout_row");
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        // Default: Integer.MIN_VALUE

        final int row = getInteger(attr.getValue(),attrCtx.getContext());

        final OneTimeAttrProcess oneTimeAttrProcess = attrCtx.getOneTimeAttrProcess();
        Runnable task = new Runnable(){
            @Override
            public void run()
            {
                OneTimeAttrProcessChildGridLayout oneTimeAttrProcessGrid = (OneTimeAttrProcessChildGridLayout) oneTimeAttrProcess;
                if (oneTimeAttrProcessGrid.gridLayout_rowSpec == null)
                    oneTimeAttrProcessGrid.gridLayout_rowSpec = new GridLayout_rowSpec();

                oneTimeAttrProcessGrid.gridLayout_rowSpec.layout_row = row;
            }};

        if (oneTimeAttrProcess != null)
        {
            oneTimeAttrProcess.addLayoutParamsTask(task);
        }
        else
        {
            throw new ItsNatDroidException("Attribute " + getName() + " cannot be changed post creation");
        }

    }

    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        // Cannot be changed post creation
    }
}
