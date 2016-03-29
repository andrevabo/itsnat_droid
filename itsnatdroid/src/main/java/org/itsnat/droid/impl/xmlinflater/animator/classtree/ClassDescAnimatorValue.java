package org.itsnat.droid.impl.xmlinflater.animator.classtree;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttributeMap;
import org.itsnat.droid.impl.dom.animator.DOMElemAnimator;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.animator.AttrAnimatorContext;
import org.itsnat.droid.impl.xmlinflater.animator.ClassDescAnimatorMgr;
import org.itsnat.droid.impl.xmlinflater.animator.XMLInflaterAnimator;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameSingle;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescAnimatorValue extends ClassDescAnimatorBased<ValueAnimator>
{
    public static final MapSmart<String,Integer> repeatModeMap = MapSmart.<String,Integer>create(2);
    static
    {
        repeatModeMap.put("restart", ValueAnimator.RESTART); // En el tutorial aparece "repeat", no es correcto
        repeatModeMap.put("reverse", ValueAnimator.REVERSE);
    }

    public ClassDescAnimatorValue(ClassDescAnimatorMgr classMgr, ClassDescAnimatorBased<? super Animator> parentClass)
    {
        super(classMgr, "animator", parentClass);
    }

    @Override
    public Class<ValueAnimator> getDeclaredClass()
    {
        return ValueAnimator.class;
    }

    @Override
    protected ValueAnimator createAnimatorNative(Context ctx)
    {
        return new ValueAnimator();
    }

    @Override
    protected boolean isAttributeIgnored(String namespaceURI,String name)
    {
        if (super.isAttributeIgnored(namespaceURI,name))
            return true;
        return NamespaceUtil.XMLNS_ANDROID.equals(namespaceURI) && ("valueType".equals(name) || "valueFrom".equals(name) || "valueTo".equals(name));
    }

    protected void fillAnimatorAttributes(Animator animator,DOMElemAnimator domElement,AttrAnimatorContext attrCtx)
    {
        fillAnimatorValueConstructionAttributes(animator, domElement, attrCtx);

        super.fillAnimatorAttributes(animator, domElement,attrCtx);
    }

    protected void fillAnimatorValueConstructionAttributes(Animator animator, DOMElemAnimator domElement, AttrAnimatorContext attrCtx)
    {
        /*
            Enum values used in XML attributes to indicate the value for mValueType

             private static final int VALUE_TYPE_FLOAT       = 0;
             private static final int VALUE_TYPE_INT         = 1;
             private static final int VALUE_TYPE_COLOR       = 4;
             private static final int VALUE_TYPE_CUSTOM      = 5;
        */

        // http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.0.3_r1/android/animation/AnimatorInflater.java

        DOMAttributeMap domAttributeMap = domElement.getDOMAttributeMap();


        ValueAnimator valueAnimator = (ValueAnimator)animator;

        int valueType; // En el caso de colores no se usa, se ignora
        DOMAttr valueTypeAttr = findAttribute(NamespaceUtil.XMLNS_ANDROID, "valueType",domAttributeMap);
        if (valueTypeAttr != null)
        {
            String value = valueTypeAttr.getValue();
            if ("floatType".equals(value)) valueType = 0; // VALUE_TYPE_FLOAT;
            else if ("intType".equals(value)) valueType = 1; // VALUE_TYPE_INT
            else throw new ItsNatDroidException("Value of attribute valueType unrecognized: " + value);
        }
        else valueType = 0; // VALUE_TYPE_FLOAT

        XMLInflaterAnimator xmlInflaterAnimator = attrCtx.getXMLInflaterAnimator();
        XMLInflaterRegistry xmlInflaterRegistry = xmlInflaterAnimator.getInflatedAnimator().getXMLInflaterRegistry();

        boolean hasFrom = false;
        boolean isDimensionFrom = false;
        DOMAttr valueFromAttr = findAttribute(NamespaceUtil.XMLNS_ANDROID, "valueFrom",domAttributeMap);
        if (valueFromAttr != null)
        {
            hasFrom = true;
            if (xmlInflaterRegistry.isColor(valueFromAttr))
            {
                valueType = 2; // Nos inventamos que el valor 2 es un posible VALUE_TYPE_COLOR
            }
            else
            {
                isDimensionFrom = xmlInflaterRegistry.isDimension(valueFromAttr);
            }
        }

        boolean hasTo = false;
        boolean isDimensionTo = false;
        DOMAttr valueToAttr = findAttribute(NamespaceUtil.XMLNS_ANDROID, "valueTo",domAttributeMap);
        if (valueToAttr != null)
        {
            hasTo = true;
            if (xmlInflaterRegistry.isColor(valueToAttr))
            {
                if (hasFrom && valueType != 2) throw new ItsNatDroidException("Attribute value of valueFrom has a different type than valueTo color value");
                valueType = 2; // Nos inventamos que el valor 2 es un posible VALUE_TYPE_COLOR
            }
            else
            {
                isDimensionTo = xmlInflaterRegistry.isDimension(valueToAttr);
            }
        }

        if (hasFrom || hasTo)
        {
            TypeEvaluator evaluator = null;

            if (valueType == 2) // Color
            {
                evaluator = new ArgbEvaluator(); // es mejor basarse en 5.0 http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/5.0.2_r1/android/animation/AnimatorInflater.java que en 4.0 que parece que tiene un bug (el setEvaluator DEBE llamarse DESPUES de definir el from y to )
                int valueFrom = hasFrom ? xmlInflaterRegistry.getColor(valueFromAttr, xmlInflaterAnimator) : 0;
                int valueTo = hasTo ? xmlInflaterRegistry.getColor(valueToAttr, xmlInflaterAnimator) : 0;
                if (hasFrom)
                {
                    if (hasTo) valueAnimator.setIntValues(valueFrom,valueTo);
                    else valueAnimator.setIntValues(valueFrom);
                }
                else
                {
                    valueAnimator.setIntValues(valueTo);
                }
            }
            else if (valueType == 1) // Integer
            {
                int valueFrom = 0;
                int valueTo = 0;
                if (hasFrom)
                {
                    valueFrom = isDimensionFrom ? xmlInflaterRegistry.getDimensionIntFloor(valueFromAttr, xmlInflaterAnimator) : xmlInflaterRegistry.getInteger(valueFromAttr, xmlInflaterAnimator);
                }

                if (hasTo)
                {
                    valueTo = isDimensionTo ? xmlInflaterRegistry.getDimensionIntFloor(valueToAttr, xmlInflaterAnimator) : xmlInflaterRegistry.getInteger(valueToAttr, xmlInflaterAnimator);
                }

                if (hasFrom)
                {
                    if (hasTo) valueAnimator.setIntValues(valueFrom,valueTo);
                    else valueAnimator.setIntValues(valueFrom);
                }
                else
                {
                    valueAnimator.setIntValues(valueTo);
                }
            }
            else if (valueType == 0) // Float
            {
                float valueFrom = 0f;
                float valueTo = 0f;
                if (hasFrom)
                {
                    valueFrom = isDimensionFrom ? xmlInflaterRegistry.getDimensionFloat(valueFromAttr, xmlInflaterAnimator) : xmlInflaterRegistry.getFloat(valueFromAttr, xmlInflaterAnimator);
                }

                if (hasTo)
                {
                    valueTo = isDimensionTo ? xmlInflaterRegistry.getDimensionFloat(valueToAttr, xmlInflaterAnimator) : xmlInflaterRegistry.getFloat(valueToAttr, xmlInflaterAnimator);
                }

                if (hasFrom)
                {
                    if (hasTo) valueAnimator.setFloatValues(valueFrom, valueTo);
                    else valueAnimator.setFloatValues(valueFrom);
                }
                else
                {
                    valueAnimator.setFloatValues(valueTo);
                }
            }

            if (evaluator != null)
                valueAnimator.setEvaluator(evaluator);
        }
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodInt(this, "repeatCount", 0)); // Se puede llamar independientemente de valueFrom y valueTo
        addAttrDescAN(new AttrDescReflecMethodNameSingle(this, "repeatMode", int.class, repeatModeMap, "repeat"));  // "
    }

}