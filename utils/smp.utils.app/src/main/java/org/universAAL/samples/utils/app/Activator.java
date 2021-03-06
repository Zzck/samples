/*
	Copyright 2008-2014 ITACA-TSB, http://www.tsb.upv.es
	Instituto Tecnologico de Aplicaciones de Comunicacion
	Avanzadas - Grupo Tecnologias para la Salud y el
	Bienestar (TSB)

	See the NOTICE file distributed with this work for additional
	information regarding copyright ownership

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	  http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.samples.utils.app;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.OSGiContainer;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.ontology.device.HeaterActuator;
import org.universAAL.ontology.device.StatusValue;
import org.universAAL.ontology.device.TemperatureSensor;
import org.universAAL.ontology.location.Location;
import org.universAAL.utilities.api.context.mid.UtilPublisher;

public class Activator implements BundleActivator {
	// Declare constants
	public static final String APP_NAMESPACE = "http://ontology.universAAL.org/SimpleApp.owl#";
	public static final String APP_URL = "http://www.samples.org";
	public static final String APP_NAME = "Heater App Example";
	private static final String PROVIDER_URI = APP_NAMESPACE + "MyProvider";
	private static final String HEATER_URI = APP_NAMESPACE + "MyHeater";
	private static final String LOCATION_URI = APP_NAMESPACE + "LivingRoom";
	public static final Float THERMOSTAT = 14f;
	// OSGi & universAAL contexts
	public static BundleContext osgiContext = null;
	public static ModuleContext context = null;
	// universAAL wrappers: All
	protected static UtilPublisher publisher;
	protected static CalleeExample callee;
	protected static ServiceCaller caller;
	protected static SubscriberExample subscriber;
	protected static UIExample ui;
	// The heater the app controls. It uses the ontology model directly but it
	// could be any imaginable model.
	protected static HeaterActuator heater;

	// Initialize the heater the app controls.
	static {
		heater = new HeaterActuator(HEATER_URI);
		heater.setLocation(new Location(LOCATION_URI));
		heater.setValue(StatusValue.NotActivated);
	}

	// Start the wrapping to universAAL
	public void start(BundleContext bcontext) throws Exception {
		// Get the universAAL module context
		Activator.osgiContext = bcontext;
		Activator.context = OSGiContainer.THE_CONTAINER.registerModule(new Object[] { bcontext });

		// Register the Context Publisher as controller and to send events
		publisher = new UtilPublisher(context, PROVIDER_URI, ContextProviderType.controller, HeaterActuator.MY_URI,
				HeaterActuator.PROP_HAS_VALUE, TypeMapper.getDatatypeURI(Boolean.class));
		// Create a default Service Caller. Not used yet, though
		caller = new DefaultServiceCaller(context);
		// Register the Service Callee as a typical actuator service
		callee = new CalleeExample(context, APP_NAMESPACE, heater);
		// Register the UI Caller and add its button to Main Menu
		ui = new UIExample(context, APP_NAMESPACE, APP_URL, APP_NAME);
		// Register the Context Subscriber to receive temperature events
		subscriber = new SubscriberExample(context, TemperatureSensor.MY_URI, TemperatureSensor.PROP_HAS_VALUE, null);
	}

	// Stop the wrapping to universAAL
	public void stop(BundleContext arg0) throws Exception {
		subscriber.close();
		ui.close();
		callee.close();
		caller.close();
		publisher.close();
	}
}
