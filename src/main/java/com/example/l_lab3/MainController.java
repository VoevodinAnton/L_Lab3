package com.example.l_lab3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

@Controller
public class MainController {

    @GetMapping
    public String getIndex(HttpServletRequest httpServletRequest) {
        SumOperation sumOperation = new SumOperation();

        httpServletRequest.getSession().setAttribute("MY_SESSION_NUMBERS", sumOperation);

        return "index";
    }

    @PostMapping
    public String doOperation(Model model,
                              @RequestParam("fnumber") Double fNumber,
                              @RequestParam("lnumber") Double lNumber,
                              HttpServletRequest httpServletRequest,
                              HttpSession httpSession) {

        SumOperation sumOperation = (SumOperation) httpSession.getAttribute("MY_SESSION_NUMBERS");

        if (sumOperation == null) {
            try {
                Thread.sleep(5000);
                model.addAttribute("errorMessage", "Ошибка сложения чисел");
                return "redirect:/";
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        sumOperation.setA(fNumber);
        sumOperation.setB(lNumber);

        httpServletRequest.getSession().setAttribute("MY_SESSION_NUMBERS", sumOperation);

        model.addAttribute("sumOperation", sumOperation);
        return "result";
    }

    @PostMapping("/xml")
    public String writeToXml(Model model, HttpSession httpSession) {
        SumOperation sumOperation = (SumOperation) httpSession.getAttribute("MY_SESSION_NUMBERS");

        writeToXml(sumOperation);

        model.addAttribute("sumOperation", sumOperation);
        model.addAttribute("successMessage", "Успешно выгружено в xml");
        return "result";
    }

    private void writeToXml(SumOperation sumOperation) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(SumOperation.class);

            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //Store XML to File
            File file = new File("sumOperation.xml");

            //Writes XML file to file-system
            jaxbMarshaller.marshal(sumOperation, file);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }
}
