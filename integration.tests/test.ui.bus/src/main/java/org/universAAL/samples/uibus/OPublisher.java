package org.universAAL.samples.uibus;

import java.util.Locale;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.IntRestriction;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.MediaObject;
import org.universAAL.middleware.ui.rdf.Range;
import org.universAAL.middleware.ui.rdf.Select;
import org.universAAL.middleware.ui.rdf.Select1;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.middleware.ui.rdf.TextArea;
import org.universAAL.ontology.profile.User;

public class OPublisher extends UICaller {

    private final static Logger log = LoggerFactory.getLogger(OPublisher.class);
    private static Form[] presetForms = new Form[] { getPresetForm(1),
	    getPresetForm(2), getPresetForm(3), getPresetForm(4),
	    getPresetForm(5), getPresetForm(6), getPresetForm(7),
	    getPresetForm(8), getPresetForm(9), getPresetForm(10) };

    protected OPublisher(ModuleContext context) {
	super(context);
	// TODO Auto-generated constructor stub
    }

    public void communicationChannelBroken() {
	// TODO Auto-generated method stub

    }

    // SHOWS_____________________________________________
    public void showRandomDialog(User user) {
	log.debug("Show dialog from OPub");
	Random rand = new Random();
	Form f = presetForms[rand.nextInt(10)];
	UIRequest oe = new UIRequest(user, f, null, Locale.ENGLISH,
		PrivacyLevel.insensible);
	log.debug("Publish dialog from OPub");
	sendUIRequest(oe);
    }

    public long showRandomBurst(User user, int size) {
	Random r = new Random();
	long t0 = System.currentTimeMillis();
	for (int i = 0; i < size; i++) {
	    Form f = presetForms[r.nextInt(10)];
	    UIRequest oe = new UIRequest(user, f, null, Locale.ENGLISH,
		    PrivacyLevel.insensible);
	    sendUIRequest(oe);
	}
	long t1 = System.currentTimeMillis();
	return t1 - t0;
    }

    public long showDynamicDialog(User user, int size) {
	long t0 = System.currentTimeMillis();
	Form f = getDynamicForm(size);
	UIRequest oe = new UIRequest(user, f, null, Locale.ENGLISH,
		PrivacyLevel.insensible);
	sendUIRequest(oe);
	long t1 = System.currentTimeMillis();
	return t1 - t0;
    }

    public void showAllRespDialog(User user, String[] formsNames,
	    String[] formsResults) {
	log.debug("Show result from OPub");
	Form f = getAllRespForm(formsNames, formsResults);
	UIRequest oe = new UIRequest(user, f, null, Locale.ENGLISH,
		PrivacyLevel.insensible);
	log.debug("Publish dialog from OPub");
	sendUIRequest(oe);
    }

    // FORMS_______________________________________________________
    public Form getAllRespForm(String[] formsNames, String[] formsResults) {
	Form f = Form.newDialog("Input response", (String) null);
	Group controls = f.getIOControls();
	Group submits = f.getSubmits();

	for (int i = 0; i < formsResults.length; i++) {
	    new SimpleOutput(controls, new Label(formsNames[i], (String) null),
		    null, formsResults[i]);
	}
	new Submit(submits, new Label("OK", (String) null), "testresp");

	return f;

    }

    private static Form getPresetForm(int size) {
	Form f = Form.newDialog("Form tester", (String) null);
	Group controls = f.getIOControls();
	Group submits = f.getSubmits();
	int i = 0;
	if (i++ <= size) {
	    new SimpleOutput(controls,
		    new Label("Simple Output", (String) null), null,
		    "Simple Output with a Label");
	}
	if (i++ <= size) {
	    new InputField(
		    controls,
		    new Label("Input field", (String) null),
		    new PropertyPath(
			    null,
			    false,
			    new String[] { "http://ontology.aal-persona.org/Tests.owl#input1" }),
		    null, null);
	}
	if (i++ <= size) {
	    Select1 s1 = new Select1(
		    controls,
		    new Label("Select1", (String) null),
		    new PropertyPath(
			    null,
			    false,
			    new String[] { "http://ontology.aal-persona.org/Tests.owl#input4" }),
		    null, null);
	    s1.generateChoices(new String[] { "Opt1", "Opt2", "Opt3" });
	}
	if (i++ <= size) {
	    Select ms1 = new Select(
		    controls,
		    new Label("Select", (String) null),
		    new PropertyPath(
			    null,
			    false,
			    new String[] { "http://ontology.aal-persona.org/Tests.owl#input6" }),
		    null, null);
	    ms1.generateChoices(new String[] { "OptA", "OptB", "OptC" });
	}
	if (i++ <= size) {
	    // Repeat table = new Repeat(controls,new
	    // Label("Repeat table",(String)null),new
	    // PropertyPath(null,false,new
	    // String[]{"http://ontology.aal-persona.org/Tests.owl#input8"}),null,
	    // null);
	    // Group tableGroup = new Group(table, null, null, null,
	    // (Resource)null);
	    // new SimpleOutput(tableGroup,new Label("Name",(String)null),new
	    // PropertyPath(null, true, new
	    // String[]{"http://ontology.aal-persona.org/Tests.owl#input9"}),null);
	    // new SimpleOutput(tableGroup,new
	    // Label("Measurement",(String)null),new PropertyPath(null, false,
	    // new
	    // String[]{"http://ontology.aal-persona.org/Tests.owl#input10"}),null);
	}
	if (i++ <= size) {
	    new MediaObject(controls, new Label("Media", (String) null), "IMG",
		    "android.handler/button.png");
	}
	if (i++ <= size) {
	    new TextArea(
		    controls,
		    new Label("Text Area", (String) null),
		    new PropertyPath(
			    null,
			    false,
			    new String[] { "http://ontology.aal-persona.org/Tests.owl#input11" }),
		    null, null);
	}
	if (i++ <= size) {
	    new Range(
		    controls,
		    new Label("Range", (String) null),
		    new PropertyPath(
			    null,
			    false,
			    new String[] { "http://ontology.aal-persona.org/Tests.owl#input12" }),
		    MergedRestriction.getAllValuesRestrictionWithCardinality(
			    Range.PROP_VALUE_RESTRICTION, new IntRestriction(3,
				    true, 12, true), 1, 1), new Integer(5));
	}
	if (i++ <= size) {
	    Group g1 = new Group(controls, new Label("Normal group with label",
		    (String) null), null, null, (Resource) null);
	    new SimpleOutput(g1, null, null, "In g1 group");
	}
	new Submit(submits, new Label("OK", (String) null), "testsubmit"); //$NON-NLS-1$ //$NON-NLS-2$
	return f;
    }

    private static Form getDynamicForm(int size) {
	Form f = Form.newDialog("Form tester", (String) null);
	Group controls = f.getIOControls();
	Group submits = f.getSubmits();
	Random r = new Random();
	for (int i = 0; i < size; i++) {
	    switch (r.nextInt(10)) {
	    case 1:
		new SimpleOutput(controls, new Label("Simple Output",
			(String) null), null, "Simple Output with a Label");
		break;
	    case 2:
		new InputField(
			controls,
			new Label("Input field", (String) null),
			new PropertyPath(
				null,
				false,
				new String[] { "http://ontology.aal-persona.org/Tests.owl#input1" }),
			null, null);
		break;
	    case 3:
		Select1 s1 = new Select1(
			controls,
			new Label("Select1", (String) null),
			new PropertyPath(
				null,
				false,
				new String[] { "http://ontology.aal-persona.org/Tests.owl#input4" }),
			null, null);
		s1.generateChoices(new String[] { "Opt1", "Opt2", "Opt3" });
		break;
	    case 4:
		Select ms1 = new Select(
			controls,
			new Label("Select", (String) null),
			new PropertyPath(
				null,
				false,
				new String[] { "http://ontology.aal-persona.org/Tests.owl#input6" }),
			null, null);
		ms1.generateChoices(new String[] { "OptA", "OptB", "OptC" });
		break;
	    case 5:
		// Repeat table = new Repeat(controls,new
		// Label("Repeat table",(String)null),new
		// PropertyPath(null,false,new
		// String[]{"http://ontology.aal-persona.org/Tests.owl#input8"}),null,
		// null);
		// Group tableGroup = new Group(table, null, null, null,
		// (Resource)null);
		// new SimpleOutput(tableGroup,new
		// Label("Name",(String)null),new PropertyPath(null, true, new
		// String[]{"http://ontology.aal-persona.org/Tests.owl#input9"}),null);
		// new SimpleOutput(tableGroup,new
		// Label("Measurement",(String)null),new PropertyPath(null,
		// false, new
		// String[]{"http://ontology.aal-persona.org/Tests.owl#input10"}),null);
		break;
	    case 6:
		new MediaObject(controls, new Label("Media", (String) null),
			"IMG", "android.handler/button.png");
		break;
	    case 7:
		new TextArea(
			controls,
			new Label("Text Area", (String) null),
			new PropertyPath(
				null,
				false,
				new String[] { "http://ontology.aal-persona.org/Tests.owl#input11" }),
			null, null);
		break;
	    case 8:
		new Range(
			controls,
			new Label("Range", (String) null),
			new PropertyPath(
				null,
				false,
				new String[] { "http://ontology.aal-persona.org/Tests.owl#input12" }),
			MergedRestriction
				.getAllValuesRestrictionWithCardinality(
					Range.PROP_VALUE_RESTRICTION,
					new IntRestriction(3, true, 12, true),
					1, 1), new Integer(5));
		break;
	    case 9:
		Group g1 = new Group(controls, new Label(
			"Normal group with label", (String) null), null, null,
			(Resource) null);
		new SimpleOutput(g1, null, null, "In g1 group");
		break;
	    default:
		break;
	    }
	}
	new Submit(submits, new Label("OK", (String) null), "testsubmit"); //$NON-NLS-1$ //$NON-NLS-2$
	return f;
    }

    @Override
    public void dialogAborted(String dialogID) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void handleUIResponse(UIResponse input) {
	User user = (User) input.getUser();
	log.info("Received an Input Event from user {}", user.getURI());
	String submit = input.getSubmissionID();
	try {
	    // if(submit.startsWith("testsubmit")){
	    // String[] formsNames=new
	    // String[]{"Input 1","Input 2","Input 3","Select 1 1","Select 1 2","Select M 1","Select M 2","Area","Range"};
	    // String[] formsResults=new String[9];
	    // formsResults[0]=event.getUserInput(new
	    // String[]{("http://ontology.aal-persona.org/Tests.owl#input1")}).toString();
	    // formsResults[1]=event.getUserInput(new
	    // String[]{("http://ontology.aal-persona.org/Tests.owl#input2")}).toString();
	    // formsResults[2]=event.getUserInput(new
	    // String[]{("http://ontology.aal-persona.org/Tests.owl#input3")}).toString();
	    // formsResults[3]=event.getUserInput(new
	    // String[]{("http://ontology.aal-persona.org/Tests.owl#input4")}).toString();
	    // formsResults[4]=event.getUserInput(new
	    // String[]{("http://ontology.aal-persona.org/Tests.owl#input5")}).toString();
	    // formsResults[5]=event.getUserInput(new
	    // String[]{("http://ontology.aal-persona.org/Tests.owl#input6")}).toString();
	    // formsResults[6]=event.getUserInput(new
	    // String[]{("http://ontology.aal-persona.org/Tests.owl#input7")}).toString();
	    // formsResults[7]=event.getUserInput(new
	    // String[]{("http://ontology.aal-persona.org/Tests.owl#input11")}).toString();
	    // formsResults[8]=event.getUserInput(new
	    // String[]{("http://ontology.aal-persona.org/Tests.owl#input12")}).toString();
	    // Activator.uoutput.showAllRespDialog(user,formsNames,formsResults);
	    // }
	} catch (Exception e) {
	    log.error("Error while processing the user input: {}", e);
	}
    }

}
