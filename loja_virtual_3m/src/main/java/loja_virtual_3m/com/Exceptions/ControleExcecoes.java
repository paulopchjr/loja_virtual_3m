package loja_virtual_3m.com.Exceptions;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import loja_virtual_3m.com.DTO.ErrorDto;

@RestControllerAdvice
@ControllerAdvice
public class ControleExcecoes extends ResponseEntityExceptionHandler {

	/* CAPTURA EXCECOES DO PROJETO */
	@ExceptionHandler({ Exception.class, RuntimeException.class, Throwable.class })
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {

		ErrorDto errorDto = new ErrorDto();

		String msg = "";

		if (ex instanceof MethodArgumentNotValidException) {
			List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();

			for (ObjectError objectError : list) {
				msg += objectError.getDefaultMessage() + "\n";
			}
		} else {
			msg = ex.getMessage();
		}

		errorDto.setError(msg);

		HttpStatus httpStatus = HttpStatus.resolve(statusCode.hashCode());
		String reasonPhrase = (httpStatus != null) ? httpStatus.getReasonPhrase() : "Status desconhecido ";
		errorDto.setCode(statusCode.value() + "=====>" + reasonPhrase);

		return new ResponseEntity<Object>(errorDto, statusCode);
	}

	/* captura erro do banco */
	@ExceptionHandler({ DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class })
	protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex) {

		ErrorDto errorDto = new ErrorDto();

		String msg = "Erro na operação de dados";

		if (ex instanceof DataIntegrityViolationException) {
			msg = "Erro de integridade de dados: " + ex.getLocalizedMessage();

			// Erro violação de constraint(chave estrangeira/ duplicada)
			if (ex.getCause() instanceof ConstraintViolationException) {

				ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex
						.getCause();
				msg = "Erro de restrição de banco. Constraint afetada: "
						+ constraintViolationException.getConstraintName();
			}
		}
			else if (ex instanceof ConstraintViolationException) {
			msg = "Chave estrangeira ou integridade violada: "
					+ ((ConstraintViolationException) ex).getConstraintName();
		}
			else if (ex instanceof SQLException) {
				
				SQLException sqlException = (SQLException) ex;
				
			switch	(sqlException.getErrorCode()) {
				
			case 1062 : msg ="Erro: Registro duplicado detectado no banco de dados"; break;
			
			case 1451: msg = "Erro de chave estrangeira: Não é possível excluir este registro pois ele possui dependências vinculadas."; break;
			
			case 1452: msg = "Erro de chave estrangeira: Registro pai associado não encontrado."; break;
			
			default:msg = "Erro de sintaxe ou execução SQL: (Código " + sqlException.getErrorCode() + ") " + sqlException.getMessage();
			break;
			}
				
			}else {
				
				msg = ex.getMessage();
				
			}

		errorDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());

		return new ResponseEntity<Object>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
