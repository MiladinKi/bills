package bills.services;

import bills.entities.dtos.PaymentDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentReportService {
    public byte[] generateReport(List<PaymentDTO> payments) throws Exception {
        // Učitaj JRXML fajl
        InputStream reportStream = new ClassPathResource("reports/paymentReport.jrxml").getInputStream();

        // Kompajliraj JRXML u JasperReport objekat
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // Kreiraj datasource od liste DTO objekata
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(payments);

        // Parametri, ako treba
        Map<String, Object> parameters = new HashMap<>();

        // Popuni izveštaj podacima
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Exportuj u PDF i vrati bajtove
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
