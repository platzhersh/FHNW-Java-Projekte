
package ch.fhnw.ds.temp.server.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getRandomTemperatureResponse", namespace = "http://server.temp.ds.fhnw.ch/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getRandomTemperatureResponse", namespace = "http://server.temp.ds.fhnw.ch/", propOrder = {
    "resultC",
    "resultF"
})
public class GetRandomTemperatureResponse {

    @XmlElement(name = "resultC", namespace = "")
    private BigDecimal resultC;
    @XmlElement(name = "resultF", namespace = "")
    private BigDecimal resultF;

    /**
     * 
     * @return
     *     returns BigDecimal
     */
    public BigDecimal getResultC() {
        return this.resultC;
    }

    /**
     * 
     * @param resultC
     *     the value for the resultC property
     */
    public void setResultC(BigDecimal resultC) {
        this.resultC = resultC;
    }

    /**
     * 
     * @return
     *     returns BigDecimal
     */
    public BigDecimal getResultF() {
        return this.resultF;
    }

    /**
     * 
     * @param resultF
     *     the value for the resultF property
     */
    public void setResultF(BigDecimal resultF) {
        this.resultF = resultF;
    }

}
