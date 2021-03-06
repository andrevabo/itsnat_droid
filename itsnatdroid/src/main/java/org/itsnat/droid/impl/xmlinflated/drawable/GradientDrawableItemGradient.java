package org.itsnat.droid.impl.xmlinflated.drawable;

import org.itsnat.droid.impl.xmlinflater.PercFloat;

/**
 * Created by jmarranz on 27/11/14.
 */
public class GradientDrawableItemGradient extends ElementDrawableChildNormal
{
    protected Float angle;
    protected PercFloat centerX;
    protected PercFloat centerY;
    protected Integer centerColor;
    protected Integer endColor;
    protected PercFloat gradientRadius;
    protected Integer startColor;
    protected Integer type;  // GradientTypeUtil.LINEAR_GRADIENT, RADIAL_GRADIENT, SWEEP_GRADIENT
    protected Boolean useLevel;


    public GradientDrawableItemGradient(ElementDrawable parentElementDrawable)
    {
        super(parentElementDrawable);
    }

    public Float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public PercFloat getCenterX() {
        return centerX;
    }

    public void setCenterX(PercFloat centerX) {
        this.centerX = centerX;
    }

    public PercFloat getCenterY() {
        return centerY;
    }

    public void setCenterY(PercFloat centerY) {
        this.centerY = centerY;
    }

    public Integer getCenterColor() {
        return centerColor;
    }

    public void setCenterColor(int centerColor) {
        this.centerColor = centerColor;
    }

    public Integer getEndColor() {
        return endColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    public PercFloat getGradientRadius() {
        return gradientRadius;
    }

    public void setGradientRadius(PercFloat gradientRadius) {
        this.gradientRadius = gradientRadius;
    }

    public Integer getStartColor() {
        return startColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public Integer getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Boolean getUseLevel() {
        return useLevel;
    }

    public void setUseLevel(boolean useLevel) {
        this.useLevel = useLevel;
    }
}
