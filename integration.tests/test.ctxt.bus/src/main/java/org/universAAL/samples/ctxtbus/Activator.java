package org.universAAL.samples.ctxtbus;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.sodapop.msg.MessageContentSerializer;

public class Activator implements BundleActivator, ServiceListener {
    public static BundleContext context = null;
    public static CSubscriber csubscriber = null;
    public static CPublisher cpublisher = null;
    public static HistoryCaller hcaller = null;
    public static ProfileCaller pcaller = null;
    protected static GUIPanel panel;
    protected static MessageContentSerializer ser;
    private static ModuleContext moduleContext = null;
    public static MessageContentSerializer parser = null;

    public void start(BundleContext context) throws Exception {
	Activator.context = context;
	Activator.moduleContext = uAALBundleContainer.THE_CONTAINER
		.registerModule(new Object[] { context });
	ser = (MessageContentSerializer) context.getService(context
		.getServiceReference(MessageContentSerializer.class.getName()));
	csubscriber = new CSubscriber(moduleContext);
	cpublisher = new CPublisher(moduleContext);
	hcaller = new HistoryCaller(moduleContext);
	pcaller = new ProfileCaller(moduleContext);
	panel = new GUIPanel();
	panel.setVisible(true);
	// Look for MessageContentSerializer of mw.data.serialization
	String filter = "(objectclass="
		+ MessageContentSerializer.class.getName() + ")";
	context.addServiceListener(this, filter);
	ServiceReference references[] = context.getServiceReferences(null,
		filter);
	for (int i = 0; references != null && i < references.length; i++)
	    this.serviceChanged(new ServiceEvent(ServiceEvent.REGISTERED,
		    references[i]));
    }

    public void stop(BundleContext arg0) throws Exception {
	csubscriber.close();
	cpublisher.close();
	panel.dispose();
    }

    public void serviceChanged(ServiceEvent event) {
	// Update the MessageContentSerializer
	switch (event.getType()) {
	case ServiceEvent.REGISTERED:
	case ServiceEvent.MODIFIED:
	    this.parser = ((MessageContentSerializer) context.getService(event
		    .getServiceReference()));
	    break;
	case ServiceEvent.UNREGISTERING:
	    this.parser = (null);
	    break;
	}
    }

}