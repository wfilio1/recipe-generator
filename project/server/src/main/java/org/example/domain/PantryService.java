package org.example.domain;

import org.example.data.PantryRepository;
import org.example.models.Pantry;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;

@Service
public class PantryService {

    private PantryRepository pantryRepository;

    public PantryService(PantryRepository pantryRepository) throws DataAccessException {
        this.pantryRepository = pantryRepository;
    }

    public List<Pantry> findAll() throws DataAccessException {
        return pantryRepository.findAll();
    }

    public List<Pantry> findByUserId(int userId) throws DataAccessException {
        return pantryRepository.findByUserId(userId);
    }

    public Result add(Pantry pantry) throws DataAccessException {
        Result result = validate(pantry);

        if (result.isSuccess()) {
            pantry = pantryRepository.add(pantry);
            if (pantry == null) {
                result.addErrorMessage("Failed to add ingredient to pantry.", ResultType.INVALID);
            } else {
                result.setPayload(pantry);
            }
        }

        return result;
    }

    public boolean delete(int pantryId) throws DataAccessException {
        return pantryRepository.delete(pantryId);
    }

    private Result validate(Pantry pantry) throws DataAccessException {
        Result result = new Result();

        if (pantry == null) {
            result.addErrorMessage("Pantry cannot be null.", ResultType.INVALID);
            return result;
        }

        if (pantry.getUserId() == 0) {
            result.addErrorMessage("User ID is required.", ResultType.INVALID);
        }

        if (pantry.getQuantity() == 0 || pantry.getQuantity() < 0) {
            result.addErrorMessage("Quantity cannot be zero or negative", ResultType.INVALID);
        }

        if (pantry.getIngredientId() == 0 || pantry.getIngredientId() < 0) {
            result.addErrorMessage("Ingredient ID is required.", ResultType.INVALID);
        }

        if (pantry.getMeasurementId() == 0 || pantry.getMeasurementId() < 0) {
            result.addErrorMessage("Measurement unit is required.", ResultType.INVALID);
        }

        return result;
    }

}
