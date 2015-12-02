package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.view.View;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewCreateProcess;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewCreateProcessChildGridLayout;
import org.itsnat.droid.impl.xmlinflater.layout.attr.GridLayout_rowSpec;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_layout_rowSpan extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_view_View_layout_rowSpan(ClassDescViewBased parent)
    {
        super(parent,"layout_rowSpan");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        // Default: 1

        final int rowSpan = getInteger(attr.getValue(),attrCtx.getContext());

        final PendingViewCreateProcess pendingViewCreateProcess = attrCtx.getPendingViewCreateProcess();
        Runnable task = new Runnable(){
            @Override
            public void run()
            {
                PendingViewCreateProcessChildGridLayout oneTimeAttrProcessGrid = (PendingViewCreateProcessChildGridLayout) pendingViewCreateProcess;
                if (oneTimeAttrProcessGrid.gridLayout_rowSpec == null)
                    oneTimeAttrProcessGrid.gridLayout_rowSpec = new GridLayout_rowSpec();

                oneTimeAttrProcessGrid.gridLayout_rowSpec.layout_rowSpan = rowSpan;
            }};

        if (pendingViewCreateProcess != null)
        {
            pendingViewCreateProcess.addPendingLayoutParamsTask(task);
        }
        else
        {
            throw new ItsNatDroidException("Attribute " + getName() + " cannot be changed post creation");
        }
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        // cannot be changed post creation
    }
}
