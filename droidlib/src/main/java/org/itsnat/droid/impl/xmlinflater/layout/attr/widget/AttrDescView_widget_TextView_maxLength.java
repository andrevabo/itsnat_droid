package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.text.InputFilter;
import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TextView_maxLength extends AttrDescView
{
    public AttrDescView_widget_TextView_maxLength(ClassDescViewBased parent)
    {
        super(parent,"maxLength");
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        int convertedValue = getInteger(attr.getValue(),attrCtx.getContext());

        TextView textView = (TextView)view;
        if (convertedValue >= 0)
        {
            textView.setFilters(new InputFilter[] { new InputFilter.LengthFilter(convertedValue) });
        }
        else
        {
            InputFilter[] NO_FILTERS = new InputFilter[0];
            textView.setFilters(NO_FILTERS);
        }
    }

    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setToRemoveAttribute(view, "-1", attrCtx);
    }

}
