
package ch.fhnw.ds.temp.server.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "fahrenheitToCelsiusResponse", namespace = "http://server.temp.ds.fhnw.ch/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fahrenheitToCelsiusResponse", namespace = "http://server.temp.ds.fhnw.ch/")
public class FahrenheitToCelsiusResponse {

    @XmlElement(name = "arg", namespace = "")
    private BigDecimal arg;

    /**
     * 
     * @return
     *     returns BigDecimal
     */
    public BigDecimal getArg() {
        return this.arg;
    }

    /**
     * 
     * @param arg
     *     the value for the arg property
     */
    public void setArg(BigDecimal arg) {
        this.arg = arg;
    }

}
