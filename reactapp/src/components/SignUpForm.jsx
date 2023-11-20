import { Button, Card, CardBody, FormControl, FormErrorMessage, FormLabel, Heading, Input, Text, VStack } from '@chakra-ui/react'
import { Field, Form, Formik } from 'formik'
import * as Yup from "yup"
import React from 'react'
import { Link } from 'react-router-dom';
import { toast } from './Toast';

const getCharacterValidationError = (str) => {
    return `Your password must have at least 1 ${str} character`;
};

const signUpFormValidation = Yup.object().shape({
    name: Yup.string().required("Please input your name!"),
    email: Yup.string().email("Not a valid email!").required("Email is required"),
    password: Yup.string()
        .required("Please enter a password")
        // check minimum characters
        .min(8, "Password must have at least 8 characters")
        // different error messages for different requirements
        .matches(/[0-9]/, getCharacterValidationError("digit"))
        .matches(/[a-z]/, getCharacterValidationError("lowercase"))
        .matches(/[A-Z]/, getCharacterValidationError("uppercase")),
})

export const SignUpForm = () => {
    return (
        <Card w={{ md: "70%" }} size={"lg"} zIndex={"9999"}>
            <CardBody>
                <Heading fontSize={"2xl"} mb={3} textAlign={"center"}>Create Your account</Heading>
                <Formik
                    initialValues={{ name: '', email: "", password: "" }}
                    validationSchema={signUpFormValidation}
                    onSubmit={(values, actions) => {
                        setTimeout(() => {
                            alert(JSON.stringify(values, null, 2))
                            actions.setSubmitting(false)
                            toast({
                                status: "info",
                                title: "Successfully created an account.",
                                description: "You can now log in."
                            })
                        }, 1000)
                    }}
                >
                    {(props) => (
                        <VStack as={Form}>
                            <Field name='name'>
                                {({ field, form }) => (
                                    <FormControl isInvalid={form.errors.name && form.touched.name}>
                                        <FormLabel fontSize={"sm"} mb={0}>Name</FormLabel>
                                        <Input variant={"filled"} {...field} placeholder='name' />
                                        <FormErrorMessage>{form.errors.name}</FormErrorMessage>
                                    </FormControl>
                                )}
                            </Field>
                            <Field name='email'>
                                {({ field, form }) => (
                                    <FormControl isInvalid={form.errors.email && form.touched.email}>
                                        <FormLabel fontSize={"sm"} mb={0}>Email</FormLabel>
                                        <Input variant={"filled"} {...field} placeholder='email' />
                                        <FormErrorMessage>{form.errors.email}</FormErrorMessage>
                                    </FormControl>
                                )}
                            </Field>
                            <Field name='password'>
                                {({ field, form }) => (
                                    <FormControl isInvalid={form.errors.password && form.touched.password}>
                                        <FormLabel fontSize={"sm"} mb={0}>Password</FormLabel>
                                        <Input variant={"filled"} {...field} type='password' placeholder='Password' />
                                        <FormErrorMessage>{form.errors.password}</FormErrorMessage>
                                    </FormControl>
                                )}
                            </Field>
                            <Button
                                mt={4}
                                colorScheme='primary'
                                isLoading={props.isSubmitting}
                                type='submit'
                                w={"full"}
                            >
                                Sign Up
                            </Button>
                            <Text fontSize={"sm"}>Already have an account? <Button colorScheme='blue' as={Link} size={"sm"} to={"/login"} variant={"link"}>Login</Button></Text>
                        </VStack>
                    )}
                </Formik>
            </CardBody>
        </Card>
    )
}
