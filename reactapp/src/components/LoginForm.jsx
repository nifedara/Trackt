import { Button, Card, CardBody, FormControl, FormErrorMessage, FormLabel, Heading, Input, Text, VStack } from '@chakra-ui/react'
import { Field, Form, Formik } from 'formik'
import React from 'react'
import { Link } from 'react-router-dom'
import * as Yup from "yup"

const loginFormValidation = Yup.object().shape({
    email: Yup.string().email("Invalid email").required("Please enter your email!"),
    password: Yup.string().required("Please enter your password!")
})

export const LoginForm = () => {
    return (
        <Card w={{ md: "70%" }} size={"lg"} zIndex={"9999"}>
            <CardBody>
                <Heading fontSize={"2xl"} mb={3} textAlign={"center"}>Create Your account</Heading>
                <Formik
                    initialValues={{ email: "", password: "" }}
                    validationSchema={loginFormValidation}
                    onSubmit={(values, actions) => {
                        setTimeout(() => {
                            alert(JSON.stringify(values, null, 2))
                            actions.setSubmitting(false)
                        }, 1000)
                    }}
                >
                    {(props) => (
                        <VStack as={Form}>
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
                                Sign In
                            </Button>
                            <Text fontSize={"sm"}>Don&apos; have an account? <Button colorScheme='blue' as={Link} size={"sm"} to={"/signup"} variant={"link"}>signup</Button></Text>
                        </VStack>
                    )}
                </Formik>
            </CardBody>
        </Card>
    )
}
