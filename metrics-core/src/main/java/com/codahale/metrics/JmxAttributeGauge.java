package com.codahale.metrics;

import java.io.IOException;
import javax.management.JMException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * A {@link Gauge} implementation which queries an {@link MBeanServerConnection} for an attribute of an object.
 */
public class JmxAttributeGauge implements Gauge<Object>,NoCopyMetric {
    /**
   * 
   */
  private static final long serialVersionUID = -1054929768844472837L;
    private final MBeanServerConnection mBeanServerConn;
    private final ObjectName objectName;
    private final String attributeName;

    /**
     * Creates a new JmxAttributeGauge.
     *
     * @param objectName    the name of the object
     * @param attributeName the name of the object's attribute
     */
    public JmxAttributeGauge(ObjectName objectName, String attributeName) {
        this(ManagementFactory.getPlatformMBeanServer(), objectName, attributeName);
    }

    /**
     * Creates a new JmxAttributeGauge.
     *
     * @param mBeanServerConn  the {@link MBeanServerConnection}
     * @param objectName       the name of the object
     * @param attributeName    the name of the object's attribute
     */
    public JmxAttributeGauge(MBeanServerConnection mBeanServerConn, ObjectName objectName, String attributeName) {
        this.mBeanServerConn = mBeanServerConn;
        this.objectName = objectName;
        this.attributeName = attributeName;
    }

    @Override
    public Object getValue() {
        try {
            return mBeanServerConn.getAttribute(objectName, attributeName);
        } catch (IOException e) {
            return null;
        } catch (JMException e) {
            return null;
        }
    }
}
