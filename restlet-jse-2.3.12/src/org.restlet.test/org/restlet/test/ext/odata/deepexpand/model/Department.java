/**
 * Copyright 2005-2014 Restlet
 * 
 * The contents of this file are subject to the terms of one of the following
 * open source licenses: Apache 2.0 or or EPL 1.0 (the "Licenses"). You can
 * select the license that you prefer but you may not use this file except in
 * compliance with one of these Licenses.
 * 
 * You can obtain a copy of the Apache 2.0 license at
 * http://www.opensource.org/licenses/apache-2.0
 * 
 * You can obtain a copy of the EPL 1.0 license at
 * http://www.opensource.org/licenses/eclipse-1.0
 * 
 * See the Licenses for the specific language governing permissions and
 * limitations under the Licenses.
 * 
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly at
 * http://restlet.com/products/restlet-framework
 * 
 * Restlet is a registered trademark of Restlet S.A.S.
 */

package org.restlet.test.ext.odata.deepexpand.model;

import java.util.List;

import org.restlet.test.ext.odata.deepexpand.model.AuthenticatedUser;
import org.restlet.test.ext.odata.deepexpand.model.Division;
import org.restlet.test.ext.odata.deepexpand.model.Lesson;
import org.restlet.test.ext.odata.deepexpand.model.Multilingual;
import org.restlet.test.ext.odata.deepexpand.model.University;

/**
 * Generated by the generator tool for the OData extension for the Restlet
 * framework.<br>
 * 
 * @see <a
 *      href="http://praktiki.metal.ntua.gr/CoopOData/CoopOData.svc/$metadata">Metadata
 *      of the target OData service</a>
 * 
 */
public class Department {

    private int id;

    private Tracking tracking;

    private List<AuthenticatedUser> authenticatedUsers;

    private List<Division> divisions;

    private List<Lesson> lessons;

    private Multilingual name;

    private University university;

    /**
     * Constructor without parameter.
     * 
     */
    public Department() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param id
     *            The identifiant value of the entity.
     */
    public Department(int id) {
        this();
        this.id = id;
    }

    /**
     * Returns the value of the "id" attribute.
     * 
     * @return The value of the "id" attribute.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the value of the "tracking" attribute.
     * 
     * @return The value of the "tracking" attribute.
     */
    public Tracking getTracking() {
        return tracking;
    }

    /**
     * Returns the value of the "authenticatedUsers" attribute.
     * 
     * @return The value of the "authenticatedUsers" attribute.
     */
    public List<AuthenticatedUser> getAuthenticatedUsers() {
        return authenticatedUsers;
    }

    /**
     * Returns the value of the "divisions" attribute.
     * 
     * @return The value of the "divisions" attribute.
     */
    public List<Division> getDivisions() {
        return divisions;
    }

    /**
     * Returns the value of the "lessons" attribute.
     * 
     * @return The value of the "lessons" attribute.
     */
    public List<Lesson> getLessons() {
        return lessons;
    }

    /**
     * Returns the value of the "name" attribute.
     * 
     * @return The value of the "name" attribute.
     */
    public Multilingual getName() {
        return name;
    }

    /**
     * Returns the value of the "university" attribute.
     * 
     * @return The value of the "university" attribute.
     */
    public University getUniversity() {
        return university;
    }

    /**
     * Sets the value of the "id" attribute.
     * 
     * @param id
     *            The value of the "id" attribute.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the value of the "tracking" attribute.
     * 
     * @param tracking
     *            The value of the "tracking" attribute.
     */
    public void setTracking(Tracking tracking) {
        this.tracking = tracking;
    }

    /**
     * Sets the value of the "authenticatedUsers" attribute.
     * 
     * @param authenticatedUsers
     *            " The value of the "authenticatedUsers" attribute.
     */
    public void setAuthenticatedUsers(List<AuthenticatedUser> authenticatedUsers) {
        this.authenticatedUsers = authenticatedUsers;
    }

    /**
     * Sets the value of the "divisions" attribute.
     * 
     * @param divisions
     *            " The value of the "divisions" attribute.
     */
    public void setDivisions(List<Division> divisions) {
        this.divisions = divisions;
    }

    /**
     * Sets the value of the "lessons" attribute.
     * 
     * @param lessons
     *            " The value of the "lessons" attribute.
     */
    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    /**
     * Sets the value of the "name" attribute.
     * 
     * @param name
     *            " The value of the "name" attribute.
     */
    public void setName(Multilingual name) {
        this.name = name;
    }

    /**
     * Sets the value of the "university" attribute.
     * 
     * @param university
     *            " The value of the "university" attribute.
     */
    public void setUniversity(University university) {
        this.university = university;
    }

}