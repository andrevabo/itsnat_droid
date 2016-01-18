package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

/**
 * Created by jmarranz on 4/06/14.
 */
public class HttpGetPageAsyncTask extends ProcessingAsyncTask<PageRequestResult>
{
    // No hay problemas de hilos, únicamente se pasa a un objeto resultado y dicho objeto no hace nada con él durante la ejecución del hilo
    protected final PageRequestImpl pageRequest;
    protected final String url;
    protected final String pageURLBase;
    protected final HttpRequestData httpRequestData;
    protected final XMLDOMRegistry xmlDOMRegistry;
    protected final AssetManager assetManager;

    public HttpGetPageAsyncTask(PageRequestImpl pageRequest, String url)
    {
        ItsNatDroidBrowserImpl browser = pageRequest.getItsNatDroidBrowserImpl();
        ItsNatDroidImpl itsNatDroid = browser.getItsNatDroidImpl();

        // Hay que tener en cuenta que estos objetos se acceden en multihilo
        this.pageRequest = pageRequest;
        this.url = url;
        this.pageURLBase = pageRequest.getURLBase();
        this.xmlDOMRegistry = itsNatDroid.getXMLDOMRegistry();
        this.assetManager = pageRequest.getContext().getAssets();
        this.httpRequestData = new HttpRequestData(pageRequest);
    }

    protected PageRequestResult executeInBackground() throws Exception
    {
        return PageRequestImpl.executeInBackground(pageRequest,url,pageURLBase,httpRequestData,xmlDOMRegistry,assetManager);
    }


    @Override
    protected void onFinishOk(PageRequestResult result)
    {
        PageRequestImpl.onFinishOk(pageRequest,result);
    }

    @Override
    protected void onFinishError(Exception ex)
    {
        PageRequestImpl.onFinishError(pageRequest, ex);
    }
}
